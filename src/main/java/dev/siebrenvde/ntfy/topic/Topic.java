package dev.siebrenvde.ntfy.topic;

import dev.siebrenvde.ntfy.message.Message;
import dev.siebrenvde.ntfy.response.ErrorResponse;
import dev.siebrenvde.ntfy.response.PublishResponse;
import dev.siebrenvde.ntfy.util.Result;
import org.jetbrains.annotations.Contract;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a topic to publish messages to
 */
@SuppressWarnings("unused")
public sealed interface Topic permits TopicImpl {

    String DEFAULT_HOST = "https://ntfy.sh";

    /**
     * Creates a new topic
     * @param host the host
     * @param name the topic name
     * @return a new topic
     */
    @Contract(value = "_, _ -> new", pure = true)
    static Topic topic(String host, String name) {
        return new TopicImpl(host, name);
    }

    /**
     * Creates a new topic using the default host (ntfy.sh)
     * @param name the topic name
     * @return a new topic
     * @see Topic#DEFAULT_HOST
     */
    static Topic topic(String name) {
        return new TopicImpl(DEFAULT_HOST, name);
    }

    /**
     * Creates a new protected topic using basic authentication
     * @param host the host
     * @param name the topic name
     * @param username the username
     * @param password the password
     * @return a new topic
     * @see <a href="https://docs.ntfy.sh/publish/#username-password">Authentication - Username + password</a>
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    static Topic secured(String host, String name, String username, String password) {
        return new TopicImpl.Secured(host, name, username, password);
    }

    /**
     * Creates a new protected topic using an access token
     * @param host the host
     * @param name the topic name
     * @param token the access token
     * @return a new topic
     * @see <a href="https://docs.ntfy.sh/publish/#access-tokens">Authentication - Access tokens</a>
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static Topic secured(String host, String name, String token) {
        return new TopicImpl.Secured(host, name, token);
    }

    /**
     * {@return the host}
     */
    @Contract(pure = true)
    String host();

    /**
     * {@return the topic name}
     */
    @Contract(pure = true)
    String name();

    /**
     * Publishes a message to the topic
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     */
    Result<PublishResponse, ErrorResponse> publish(Message message) throws IOException, InterruptedException;

    /**
     * Publishes a message to the topic
     * @param builder the message builder
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     */
    default Result<PublishResponse, ErrorResponse> publish(Message.Builder builder) throws IOException, InterruptedException {
        return publish(builder.build());
    }

    /**
     * Publishes a message to the topic
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     */
    default Result<PublishResponse, ErrorResponse> publish(String message) throws IOException, InterruptedException {
        return publish(Message.message(message));
    }

    /**
     * Schedules a message to be published to the topic at a specified time
     * @param message the message
     * @param time the time
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    Result<PublishResponse, ErrorResponse> scheduleAt(Message message, Instant time) throws IOException, InterruptedException;

    /**
     * Schedules a message to be published to the topic at a specified time
     * @param builder the message builder
     * @param time the time
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default Result<PublishResponse, ErrorResponse> scheduleAt(Message.Builder builder, Instant time) throws IOException, InterruptedException {
        return scheduleAt(builder.build(), time);
    }

    /**
     * Schedules a message to be published to the topic at a specified time
     * @param message the message
     * @param time the time
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default Result<PublishResponse, ErrorResponse> scheduleAt(String message, Instant time) throws IOException, InterruptedException {
        return scheduleAt(Message.message(message), time);
    }

    /**
     * Schedules a message to be published to the topic after a specified delay
     * @param message the message
     * @param delay the delay
     * @param unit the temporal unit the delay is in
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    Result<PublishResponse, ErrorResponse> scheduleIn(Message message, long delay, TemporalUnit unit) throws IOException, InterruptedException;

    /**
     * Schedules a message to be published to the topic after a specified delay
     * @param builder the message builder
     * @param delay the delay
     * @param unit the temporal unit the delay is in
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default Result<PublishResponse, ErrorResponse> scheduleIn(Message.Builder builder, long delay, TemporalUnit unit) throws IOException, InterruptedException {
        return scheduleIn(builder.build(), delay, unit);
    }

    /**
     * Schedules a message to be published to the topic after a specified delay
     * @param message the message
     * @param delay the delay
     * @param unit the temporal unit the delay is in
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default Result<PublishResponse, ErrorResponse> scheduleIn(String message, long delay, TemporalUnit unit) throws IOException, InterruptedException {
        return scheduleIn(Message.message(message), delay, unit);
    }

    /**
     * Asynchronously publishes a message to the topic
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     */
    CompletableFuture<Result<PublishResponse, ErrorResponse>> publishAsync(Message message) throws FileNotFoundException;

    /**
     * Asynchronously publishes a message to the topic
     * @param builder the message builder
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> publishAsync(Message.Builder builder) throws FileNotFoundException {
        return publishAsync(builder.build());
    }

    /**
     * Asynchronously publishes a message to the topic
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> publishAsync(String message) throws FileNotFoundException {
        return publishAsync(Message.message(message));
    }

    /**
     * Asynchronously schedules a message to be published to the topic at a specified time
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleAtAsync(Message message, Instant time) throws FileNotFoundException;

    /**
     * Asynchronously schedules a message to be published to the topic at a specified time
     * @param builder the message builder
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleAtAsync(Message.Builder builder, Instant time) throws FileNotFoundException {
        return scheduleAtAsync(builder.build(), time);
    }

    /**
     * Asynchronously schedules a message to be published to the topic at a specified time
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleAtAsync(String message, Instant time) throws FileNotFoundException {
        return scheduleAtAsync(Message.message(message), time);
    }

    /**
     * Asynchronously schedules a message to be published to the topic after a specified delay
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleInAsync(Message message, long delay, TemporalUnit unit) throws FileNotFoundException;

    /**
     * Asynchronously schedules a message to be published to the topic after a specified delay
     * @param builder the message builder
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleInAsync(Message.Builder builder, long delay, TemporalUnit unit) throws FileNotFoundException {
        return scheduleInAsync(builder.build(), delay, unit);
    }

    /**
     * Asynchronously schedules a message to be published to the topic after a specified delay
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @throws FileNotFoundException if the file for a file attachment was not found
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleInAsync(String message, long delay, TemporalUnit unit) throws FileNotFoundException {
        return scheduleInAsync(Message.message(message), delay, unit);
    }

}
