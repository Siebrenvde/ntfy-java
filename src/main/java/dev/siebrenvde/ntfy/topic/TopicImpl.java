package dev.siebrenvde.ntfy.topic;

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
import dev.siebrenvde.ntfy.response.Response;
import dev.siebrenvde.ntfy.response.SuccessResponse;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.siebrenvde.ntfy.util.Util.checkNotNull;

class TopicImpl implements Topic {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private final String host;
    private final String name;
    private final URI uri;

    TopicImpl(String host, String name) {
        checkNotNull(host, "host");
        checkNotNull(name, "name");
        this.host = host;
        this.name = name;
        try {
            this.uri = new URI(host).resolve(name);
            //noinspection ResultOfMethodCallIgnored
            this.uri.toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("invalid topic '" + host + "'", e);
        }
    }

    @Override
    public String host() {
        return host;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Response publish(Message message) throws IOException, InterruptedException {
        return sendRequest(message, null);
    }

    @Override
    public Response scheduleAt(Message message, Instant time) throws IOException, InterruptedException {
        return sendRequest(message, time);
    }

    @Override
    public Response scheduleIn(Message message, long delay, TemporalUnit unit) throws IOException, InterruptedException {
        return sendRequest(message, Instant.now().plus(delay, unit));
    }

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    private Response sendRequest(Message message, @Nullable Instant time) throws IOException, InterruptedException {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);

        if (message.body() != null) {
            builder.header("Message", encodeBase64(message.body()));
        }

        if (message.title() != null) {
            builder.header("Title", encodeBase64(message.title()));
        }

        if (message.priority() != Priority.DEFAULT) {
            builder.header("Priority", message.priority().name());
        }

        if (!message.tags().isEmpty()) {
            builder.header("Tags", encodeBase64(String.join(",", message.tags())));
        }

        if (message.markdown()) {
            builder.header("Markdown", "true");
        }

        if (!message.actions().isEmpty()) {
            List<String> actions = new ArrayList<>();

            for (Action action : message.actions()) {
                Map<String, String> parts = new HashMap<>();

                parts.put("action", action.action());
                parts.put("label", action.label());
                if (action.clear()) parts.put("clear", "true");

                if (action instanceof ViewAction view) {
                    parts.put("url", view.url());
                }

                else if (action instanceof BroadcastAction broadcast) {
                    if (!broadcast.intent().equals(BroadcastAction.DEFAULT_INTENT)) {
                        parts.put("intent", broadcast.intent());
                    }

                    if (!broadcast.extras().isEmpty()) {
                        broadcast.extras().forEach((key, value) -> parts.put("extras." + key, value));
                    }
                }

                else if (action instanceof HttpAction http) {
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

            builder.header("Actions", encodeBase64(String.join(";", actions)));
        }

        if (message.clickAction() != null) {
            builder.header("Click", encodeBase64(message.clickAction()));
        }

        Attachment attachment = message.attachment();
        if (attachment != null) {
            if (attachment.fileName() != null) {
                builder.header("Filename", encodeBase64(attachment.fileName()));
            }

            if (attachment instanceof UrlAttachment url) {
                builder.header("Attach", encodeBase64(url.url()));
                builder.POST(BodyPublishers.noBody());
            }

            else if (attachment instanceof FileAttachment file) {
                builder.PUT(BodyPublishers.ofFile(file.file()));

                if (file.fileName() == null) {
                    builder.header("Filename", encodeBase64(file.file().getFileName().toString()));
                }
            }
        } else {
            builder.POST(BodyPublishers.noBody());
        }

        if (message.icon() != null) {
            builder.header("Icon", encodeBase64(message.icon()));
        }

        if (message.email() != null) {
            builder.header("Email", encodeBase64(message.email()));
        }

        if (message.phone() != null) {
            builder.header("Call", encodeBase64(message.phone()));
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

        if (this instanceof Secured auth) {
            builder.header("Authorization", auth.header);
        }

        HttpResponse<String> response = CLIENT.send(builder.build(), BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return SuccessResponse.fromJson(response.body());
        } else {
            return ErrorResponse.fromJson(response.body());
        }
    }

    private String encodeBase64(String input) {
        for (int i = 0; i < input.length(); i++) {
            int c = input.codePointAt(i);
            if (c < 32 || c > 126) {
                return "=?UTF-8?B?" + Base64.getEncoder().encodeToString(input.getBytes()) + "?=";
            }
        }
        return input;
    }

    static class Secured extends TopicImpl {

        private final String header;

        Secured(String host, String name, String username, String password) {
            super(host, name);
            this.header = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        }

        Secured(String host, String name, String token) {
            super(host, name);
            this.header = "Bearer " + token;
        }

    }

}
