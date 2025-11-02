package dev.siebrenvde.ntfy.topic;

import dev.siebrenvde.ntfy.internal.BuildParameters;
import dev.siebrenvde.ntfy.message.Message;
import dev.siebrenvde.ntfy.message.Priority;
import dev.siebrenvde.ntfy.message.action.Action;
import dev.siebrenvde.ntfy.message.action.BroadcastAction;
import dev.siebrenvde.ntfy.message.action.HttpAction;
import dev.siebrenvde.ntfy.message.action.ViewAction;
import dev.siebrenvde.ntfy.message.attachment.Attachment;
import dev.siebrenvde.ntfy.message.attachment.FileAttachment;
import dev.siebrenvde.ntfy.message.attachment.UrlAttachment;
import dev.siebrenvde.ntfy.response.ErrorResponse;
import dev.siebrenvde.ntfy.response.PublishResponse;
import dev.siebrenvde.ntfy.util.Result;
import org.jspecify.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static dev.siebrenvde.ntfy.internal.Util.checkArgument;
import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

sealed class TopicImpl implements Topic permits TopicImpl.Protected {

    private static final HttpClient DEFAULT_CLIENT = HttpClient.newHttpClient();
    private static final String USER_AGENT = "ntfy-java/" + BuildParameters.VERSION + " (https://github.com/Siebrenvde/ntfy-java)";

    private final String host;
    private final String name;
    private final URI uri;
    private final HttpClient client;
    private final @Nullable Duration timeout;

    TopicImpl(final String host, final String name, final HttpClient client, @Nullable final Duration timeout) {
        this.host = host;
        this.name = name;
        try {
            this.uri = new URI(host).resolve(name);
            //noinspection ResultOfMethodCallIgnored
            this.uri.toURL();
        } catch (final URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("invalid topic '" + name + "'", e);
        }
        this.client = client;
        this.timeout = timeout;
    }

    @Override
    public String host() {
        return this.host;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Result<PublishResponse, ErrorResponse> publish(final Message message) throws IOException, InterruptedException {
        return this.sendRequest(message, null);
    }

    @Override
    public Result<PublishResponse, ErrorResponse> scheduleAt(final Message message, final Instant time) throws IOException, InterruptedException {
        return this.sendRequest(message, time);
    }

    @Override
    public Result<PublishResponse, ErrorResponse> scheduleIn(final Message message, final long delay, final TemporalUnit unit) throws IOException, InterruptedException {
        return this.sendRequest(message, Instant.now().plus(delay, unit));
    }

    @Override
    public CompletableFuture<Result<PublishResponse, ErrorResponse>> publishAsync(final Message message) {
        return this.sendRequestAsync(message, null);
    }

    @Override
    public CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleAtAsync(final Message message, final Instant time) {
        return this.sendRequestAsync(message, time);
    }

    @Override
    public CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleInAsync(final Message message, final long delay, final TemporalUnit unit) {
        return this.sendRequestAsync(message, Instant.now().plus(delay, unit));
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this.name)
            .host(this.host)
            .httpClient(this.client)
            .timeout(this.timeout);
    }

    private Result<PublishResponse, ErrorResponse> sendRequest(final Message message, @Nullable final Instant time) throws IOException, InterruptedException {
        final HttpResponse<String> response = this.client.send(this.createRequest(message, time), BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return Result.success(PublishResponse.fromJson(response.body()));
        } else {
            return Result.error(ErrorResponse.fromJson(response.body()));
        }
    }

    private CompletableFuture<Result<PublishResponse, ErrorResponse>> sendRequestAsync(final Message message, @Nullable final Instant time) {
        final HttpRequest request;
        try {
            request = this.createRequest(message, time);
        } catch (final FileNotFoundException e) {
            return CompletableFuture.failedFuture(e);
        }
        return this.client.sendAsync(request, BodyHandlers.ofString())
            .thenApply(response -> {
                if (response.statusCode() == 200) {
                    return Result.success(PublishResponse.fromJson(response.body()));
                } else {
                    return Result.error(ErrorResponse.fromJson(response.body()));
                }
            });
    }

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    private HttpRequest createRequest(final Message message, @Nullable final Instant time) throws FileNotFoundException {
        final HttpRequest.Builder builder = HttpRequest.newBuilder(this.uri);

        builder.header("User-Agent", USER_AGENT);

        if (message.body() != null) {
            builder.header("Message", this.encodeBase64(message.body()));
        }

        if (message.title() != null) {
            builder.header("Title", this.encodeBase64(message.title()));
        }

        if (message.priority() != Priority.DEFAULT) {
            builder.header("Priority", message.priority().name());
        }

        if (!message.tags().isEmpty()) {
            builder.header("Tags", this.encodeBase64(String.join(",", message.tags())));
        }

        if (message.markdown()) {
            builder.header("Markdown", "true");
        }

        if (!message.actions().isEmpty()) {
            final List<String> actions = new ArrayList<>();

            for (final Action action : message.actions()) {
                final Map<String, String> parts = new HashMap<>();

                parts.put("action", action.action());
                parts.put("label", action.label());
                if (action.clear()) parts.put("clear", "true");

                if (action instanceof final ViewAction view) {
                    parts.put("url", view.url());
                } else if (action instanceof final BroadcastAction broadcast) {
                    if (!broadcast.intent().equals(BroadcastAction.DEFAULT_INTENT)) {
                        parts.put("intent", broadcast.intent());
                    }

                    if (!broadcast.extras().isEmpty()) {
                        broadcast.extras().forEach((key, value) -> parts.put("extras." + key, value));
                    }
                } else if (action instanceof final HttpAction http) {
                    parts.put("url", http.url());

                    if (http.method() != HttpAction.DEFAULT_METHOD) {
                        parts.put("method", http.method().name());
                    }

                    if (!http.headers().isEmpty()) {
                        http.headers().forEach((header, value) -> parts.put("headers." + header, value));
                    }

                    if (http.body() != null) {
                        parts.put("body", http.body());
                    }
                }

                actions.add(String.join(
                    ",",
                    parts.entrySet().stream().map(entry -> {
                        String value = entry.getValue();
                        if (value.contains(",") || value.contains(";") || value.contains("\"")) {
                            value = value.replace("\"", "\\\"");
                            value = "\"" + value + "\"";
                        }
                        return entry.getKey() + "=" + value;
                    }).toList()
                ));
            }

            builder.header("Actions", this.encodeBase64(String.join(";", actions)));
        }

        if (message.clickAction() != null) {
            builder.header("Click", this.encodeBase64(message.clickAction()));
        }

        final Attachment attachment = message.attachment();
        if (attachment != null) {
            if (attachment.fileName() != null) {
                builder.header("Filename", this.encodeBase64(attachment.fileName()));
            }

            if (attachment instanceof final UrlAttachment url) {
                builder.header("Attach", this.encodeBase64(url.url()));
                builder.POST(BodyPublishers.noBody());
            } else if (attachment instanceof final FileAttachment file) {
                builder.PUT(BodyPublishers.ofFile(file.file()));

                if (file.fileName() == null) {
                    builder.header("Filename", this.encodeBase64(file.file().getFileName().toString()));
                }
            }
        } else {
            builder.POST(BodyPublishers.noBody());
        }

        if (message.icon() != null) {
            builder.header("Icon", this.encodeBase64(message.icon()));
        }

        if (message.email() != null) {
            builder.header("Email", this.encodeBase64(message.email()));
        }

        if (message.phone() != null) {
            builder.header("Call", this.encodeBase64(message.phone()));
        }

        if (!message.cache()) {
            builder.header("Cache", "no");
        }

        if (!message.firebase()) {
            builder.header("Firebase", "no");
        }

        if (time != null) {
            builder.header("Delay", String.valueOf(time.getEpochSecond()));
        }

        if (this instanceof final Protected auth) {
            builder.header("Authorization", auth.header);
        }

        if (this.timeout != null) {
            builder.timeout(this.timeout);
        }

        return builder.build();
    }

    private String encodeBase64(final String input) {
        for (int i = 0; i < input.length(); i++) {
            final int c = input.codePointAt(i);
            if (c < 32 || c > 126) {
                return "=?UTF-8?B?" + Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8)) + "?=";
            }
        }
        return input;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final TopicImpl topic)) return false;
        return Objects.equals(this.host, topic.host) && Objects.equals(this.name, topic.name) && Objects.equals(this.uri, topic.uri) && Objects.equals(this.client, topic.client) && Objects.equals(this.timeout, topic.timeout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.host, this.name, this.uri, this.client, this.timeout);
    }

    static final class Protected extends TopicImpl {

        private final String header;

        Protected(final String host, final String name, final HttpClient client, @Nullable final Duration timeout, final String username, final String password) {
            super(host, name, client, timeout);
            this.header = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        }

        Protected(final String host, final String name, final HttpClient client, @Nullable final Duration timeout, final String token) {
            super(host, name, client, timeout);
            this.header = "Bearer " + token;
        }

        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof final Protected that)) return false;
            if (!super.equals(o)) return false;
            return Objects.equals(this.header, that.header);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), this.header);
        }

    }

    static final class BuilderImpl implements Topic.Builder {

        private String host = Topic.DEFAULT_HOST;
        private final String name;
        private HttpClient client = DEFAULT_CLIENT;
        private @Nullable Duration timeout;
        private @Nullable String token;
        private @Nullable String username;
        private @Nullable String password;

        BuilderImpl(final String name) {
            checkNotNull(name, "name");
            this.name = name;
        }

        @Override
        public Builder host(final String host) {
            checkNotNull(host, "host");
            this.host = host;
            return this;
        }

        @Override
        public Builder httpClient(final HttpClient client) {
            checkNotNull(client, "client");
            this.client = client;
            return this;
        }

        @Override
        public Builder timeout(@Nullable final Duration timeout) {
            if (timeout != null) {
                checkArgument(!timeout.isNegative(), "timeout must be positive");
                checkArgument(!timeout.isZero(), "timeout must not be zero");
            }
            this.timeout = timeout;
            return this;
        }

        @Override
        public Builder token(final String token) {
            checkNotNull(token, "token");
            this.token = token;
            return this;
        }

        @Override
        public Builder username(final String username) {
            checkNotNull(username, "username");
            this.username = username;
            return this;
        }

        @Override
        public Builder password(final String password) {
            checkNotNull(password, "password");
            this.password = password;
            return this;
        }

        @Override
        public Topic build() {
            if (this.token == null && this.username == null && this.password == null) {
                return new TopicImpl(this.host, this.name, this.client, this.timeout);
            }
            if (this.token != null && (this.username != null || this.password != null)) {
                throw new IllegalStateException("Topic cannot have both token and basic authentication");
            }
            if (this.token != null) {
                return new Protected(this.host, this.name, this.client, this.timeout, this.token);
            }
            if (this.username != null && this.password != null) {
                return new Protected(this.host, this.name, this.client, this.timeout, this.username, this.password);
            }
            throw new IllegalStateException(
                (this.username != null ? "Username" : "Password")
                    + " provided without "
                    + (this.username != null ? "password" : "username")
            );
        }

    }

}
