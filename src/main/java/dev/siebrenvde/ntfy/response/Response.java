package dev.siebrenvde.ntfy.response;

/**
 * Represents a response from the server
 */
@SuppressWarnings("unused")
public interface Response {

    /**
     * {@return whether the response is a success response}
     * <p>
     * When <code>true</code>, {@link #asSuccess()} will return a valid {@link SuccessResponse} object
     */
    boolean isSuccess();

    /**
     * {@return whether the response is an error response}
     * <p>
     * When <code>true</code>, {@link #asError()} will return a valid {@link ErrorResponse} object
     */
    default boolean isError() {
        return !isSuccess();
    }

    /**
     * When {@link #isSuccess()} is <code>true</code>, this will return a {@link SuccessResponse},
     * otherwise it will throw an {@link IllegalStateException}
     * @return a success response
     */
    SuccessResponse asSuccess();

    /**
     * When {@link #isError()} is <code>true</code>, this will return an {@link ErrorResponse},
     * otherwise it will throw an {@link IllegalStateException}
     * @return an error response
     */
    ErrorResponse asError();

}
