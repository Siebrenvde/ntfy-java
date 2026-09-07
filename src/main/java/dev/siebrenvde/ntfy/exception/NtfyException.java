package dev.siebrenvde.ntfy.exception;

import dev.siebrenvde.ntfy.internal.ErrorResponse;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

/**
 * An exception thrown when the ntfy server returns an error
 */
public final class NtfyException extends RuntimeException {

    private final int code;
    private final int http;
    private final String error;
    private final @Nullable String link;

    @ApiStatus.Internal
    public NtfyException(final int code, final int http, final String error, final @Nullable String link) {
        super(error);
        this.code = code;
        this.http = http;
        this.error = error;
        this.link = link;
    }

    @ApiStatus.Internal
    public NtfyException(final ErrorResponse error) {
        this(error.code(), error.http(), error.error(), error.link());
    }

    /**
     * {@return the ntfy error code}
     */
    @Contract(pure = true)
    public int code() {
        return this.code;
    }

    /**
     * {@return the HTTP error code}
     */
    @Contract(pure = true)
    public int http() {
        return this.http;
    }

    /**
     * {@return a description of the error}
     */
    @Contract(pure = true)
    public String error() {
        return this.error;
    }

    /**
     * {@return a link to the documentation that may explain the issue}
     */
    @Contract(pure = true)
    public @Nullable String link() {
        return this.link;
    }

}
