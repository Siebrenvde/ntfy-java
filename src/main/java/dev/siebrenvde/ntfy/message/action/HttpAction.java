package dev.siebrenvde.ntfy.message.action;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * Represents an HTTP request action
 * @see <a href="https://docs.ntfy.sh/publish/#send-http-request">Send HTTP request</a>
 */
public interface HttpAction extends Action {

    Method DEFAULT_METHOD = Method.POST;

    /**
     * {@return the url}
     */
    @Contract(pure = true)
    String url();

    /**
     * {@return the request method}
     */
    @Contract(pure = true)
    Method method();

    /**
     * {@return the headers}
     */
    @Contract(pure = true)
    @Unmodifiable Map<String, String> headers();

    /**
     * {@return the body}
     */
    @Contract(pure = true)
    @Nullable String body();

    /**
     * An enum representing the available request methods
     */
    enum Method {
        GET,
        HEAD,
        POST,
        PUT,
        DELETE,
        CONNECT,
        OPTIONS,
        TRACE,
        PATCH
    }

    /**
     * Builder for {@link HttpAction}
     */
    @SuppressWarnings("unused")
    interface Builder {

        /**
         * Sets the request method
         * @param method the method
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder method(Method method);

        /**
         * Sets all headers, overwriting any previously set headers
         * @param headers the headers
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder headers(Map<String, String> headers);

        /**
         * Sets a header
         * @param header the header
         * @param value the value
         * @return the builder
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder setHeader(String header, String value);

        /**
         * Sets the body
         * @param body the body
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder body(String body);

        /**
         * Sets whether to clear the notification after the button is tapped
         * @param clear whether to clear the notification after the button is tapped
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder clear(boolean clear);

        /**
         * Builds the action
         * @return a new action
         */
        @Contract(value = "-> new", pure = true)
        HttpAction build();

    }

}
