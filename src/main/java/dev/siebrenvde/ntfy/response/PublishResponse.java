package dev.siebrenvde.ntfy.response;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

/**
 * Represents a response from the server when a message was successfully published
 */
public interface PublishResponse {

    /**
     * {@return the message id}
     */
    String id();

    /**
     * {@return the time at which the message was or will be published}
     */
    Instant time();

    /**
     * {@return the time at which the message will be deleted, or <code>null</code> if caching is disabled}
     */
    @Nullable Instant expires();

    @ApiStatus.Internal
    static PublishResponse fromJson(String json) {
        return PublishResponseImpl.fromJson(json);
    }

}
