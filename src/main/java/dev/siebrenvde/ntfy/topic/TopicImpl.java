package dev.siebrenvde.ntfy.topic;

import dev.siebrenvde.ntfy.message.Message;
import dev.siebrenvde.ntfy.message.Priority;
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
import java.util.Base64;

import static dev.siebrenvde.ntfy.Util.checkArgument;

class TopicImpl implements Topic {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    private final String host;
    private final String name;
    private final URI uri;

    @SuppressWarnings("ConstantValue")
    TopicImpl(String host, String name) {
        checkArgument(host != null, "host cannot be null");
        checkArgument(name != null, "name cannot be null");
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
    public void publish(Message message) {
        sendRequest(message, null);
    }

    @Override
    public void scheduleAt(Message message, Instant time) {
        sendRequest(message, time);
    }

    @Override
    public void scheduleIn(Message message, long delay, TemporalUnit unit) {
        sendRequest(message, Instant.now().plus(delay, unit));
    }

    @SuppressWarnings("UastIncorrectHttpHeaderInspection")
    private void sendRequest(Message message, @Nullable Instant time) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);

        if (message.body() != null) {
            builder.POST(BodyPublishers.ofString(message.body()));
        } else {
            builder.POST(BodyPublishers.noBody());
        }

        if (message.title() != null) {
            builder.header("Title", message.title());
        }

        if (message.priority() != Priority.DEFAULT) {
            builder.header("Priority", message.priority().name());
        }

        if (message.tags().length > 0) {
            builder.header("Tags", String.join(",", message.tags()));
        }

        if (message.markdown()) {
            builder.header("Markdown", "true");
        }

        if (time != null) {
            builder.header("Delay", String.valueOf(time.getEpochSecond()));
        }

        if (this instanceof Secured auth) {
            builder.header("Authorization", auth.header);
        }

        try {
            HttpResponse<String> response = CLIENT.send(builder.build(), BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
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
