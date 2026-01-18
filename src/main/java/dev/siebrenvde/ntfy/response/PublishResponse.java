package dev.siebrenvde.ntfy.response;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

/**
 * Represents a response from the server when a message was successfully published
 */
public sealed interface PublishResponse permits PublishResponseImpl {

    /**
     * {@return the message id}
     */
    @Contract(pure = true)
    String id();

    /**
     * {@return the time at which the message was or will be published}
     */
    @Contract(pure = true)
    Instant time();

    /**
     * {@return the time at which the message will be deleted, or <code>null</code> if caching is disabled}
     */
    @Contract(pure = true)
    @Nullable Instant expires();

    @ApiStatus.Internal
    static PublishResponse fromJson(final String json) {
        return PublishResponseImpl.fromJson(json);
    }

}
