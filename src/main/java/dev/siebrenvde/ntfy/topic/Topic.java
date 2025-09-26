package dev.siebrenvde.ntfy.topic;

import dev.siebrenvde.ntfy.message.Message;
import dev.siebrenvde.ntfy.response.ErrorResponse;
import dev.siebrenvde.ntfy.response.PublishResponse;
import dev.siebrenvde.ntfy.util.Result;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a topic to publish messages to
 */
public sealed interface Topic permits TopicImpl {

    String DEFAULT_HOST = "https://ntfy.sh";

    /**
     * Creates a new topic builder
     * @param name the topic name
     * @return a topic builder
     */
    @Contract(value = "_ -> new", pure = true)
    static Builder topic(String name) {
        return new TopicImpl.BuilderImpl(name);
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
     */
    CompletableFuture<Result<PublishResponse, ErrorResponse>> publishAsync(Message message);

    /**
     * Asynchronously publishes a message to the topic
     * @param builder the message builder
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> publishAsync(Message.Builder builder) {
        return publishAsync(builder.build());
    }

    /**
     * Asynchronously publishes a message to the topic
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> publishAsync(String message) {
        return publishAsync(Message.message(message));
    }

    /**
     * Asynchronously schedules a message to be published to the topic at a specified time
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleAtAsync(Message message, Instant time);

    /**
     * Asynchronously schedules a message to be published to the topic at a specified time
     * @param builder the message builder
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleAtAsync(Message.Builder builder, Instant time) {
        return scheduleAtAsync(builder.build(), time);
    }

    /**
     * Asynchronously schedules a message to be published to the topic at a specified time
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleAtAsync(String message, Instant time) {
        return scheduleAtAsync(Message.message(message), time);
    }

    /**
     * Asynchronously schedules a message to be published to the topic after a specified delay
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleInAsync(Message message, long delay, TemporalUnit unit);

    /**
     * Asynchronously schedules a message to be published to the topic after a specified delay
     * @param builder the message builder
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleInAsync(Message.Builder builder, long delay, TemporalUnit unit) {
        return scheduleInAsync(builder.build(), delay, unit);
    }

    /**
     * Asynchronously schedules a message to be published to the topic after a specified delay
     * @param message the message
     * @return a {@link Result} with either a {@link PublishResponse} or an {@link ErrorResponse}
     * @see <a href="https://docs.ntfy.sh/publish/#scheduled-delivery">Scheduled delivery</a>
     */
    default CompletableFuture<Result<PublishResponse, ErrorResponse>> scheduleInAsync(String message, long delay, TemporalUnit unit) {
        return scheduleInAsync(Message.message(message), delay, unit);
    }

    /**
     * Builder for {@link Topic}
     */
    sealed interface Builder permits TopicImpl.BuilderImpl {

        /**
         * Sets the host
         * <p>
         * If no host is provided the default host (ntfy.sh) is used
         * @param host the host
         * @return the builder
         * @see Topic#DEFAULT_HOST
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder host(String host);

        /**
         * Sets the http client
         * @param client the http client
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder httpClient(HttpClient client);

        /**
         * Sets the timeout used when publishing a message
         * <p>
         * Must be positive and non-zero
         * @param timeout the timeout
         * @return the builder
         * @see HttpRequest.Builder#timeout(Duration)
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder timeout(@Nullable Duration timeout);


        /**
         * Sets the access token to use for bearer authentication
         * <p>
         * Cannot be used together with {@link #username(String)} and {@link #password(String)}
         * @param token the token
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#access-tokens">Authentication - Access tokens</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder token(String token);

        /**
         * Sets the username to use for basic authentication
         * <p>
         * Must be used together with {@link #password(String)}
         * <p>
         * Cannot be used together with {@link #token(String)}
         * @param username the username
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#username-password">Authentication - Username + password</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder username(String username);

        /**
         * Sets the password to use for basic authentication
         * <p>
         * Must be used together with {@link #username(String)}
         * <p>
         * Cannot be used together with {@link #token(String)}
         * @param password the password
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#username-password">Authentication - Username + password</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder password(String password);

        /**
         * Builds the topic
         * @return a new topic
         */
        @Contract(value = "-> new", pure = true)
        Topic build();

    }

}
