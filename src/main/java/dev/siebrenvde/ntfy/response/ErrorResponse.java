package dev.siebrenvde.ntfy.response;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

/**
 * Represents an error returned by the server when a message was not successfully published
 */
public sealed interface ErrorResponse permits ErrorResponseImpl {

    /**
     * {@return the ntfy error code}
     */
    @Contract(pure = true)
    int code();

    /**
     * {@return the HTTP error code}
     */
    @Contract(pure = true)
    int http();

    /**
     * {@return a description of the error}
     */
    @Contract(pure = true)
    String error();

    /**
     * {@return a link to the documentation that may explain the issue}
     */
    @Contract(pure = true)
    @Nullable String link();

    @ApiStatus.Internal
    static ErrorResponse fromJson(final String json) {
        return ErrorResponseImpl.fromJson(json);
    }

}
