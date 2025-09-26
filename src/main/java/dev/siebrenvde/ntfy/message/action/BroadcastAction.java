package dev.siebrenvde.ntfy.message.action;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

/**
 * Represents an Android broadcast action
 * @see <a href="https://docs.ntfy.sh/publish/#send-android-broadcast">Send Android broadcast</a>
 */
public sealed interface BroadcastAction extends Action permits BroadcastActionImpl {

    String DEFAULT_INTENT = "io.heckel.ntfy.USER_ACTION";

    /**
     * {@return the intent}
     */
    @Contract(pure = true)
    String intent();

    /**
     * {@return the extras}
     */
    @Contract(pure = true)
    @Unmodifiable Map<String, String> extras();

    /**
     * Builder for {@link BroadcastAction}
     */
    interface Builder {

        /**
         * Sets the intent
         * @param intent the intent
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder intent(String intent);

        /**
         * Sets all extras, overwriting any previously set extras
         * @param extras the extras
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder extras(Map<String, String> extras);

        /**
         * Sets an extra
         * @param key the key
         * @param value the value
         * @return the builder
         */
        @Contract(value = "_, _ -> this", mutates = "this")
        Builder setExtra(String key, String value);

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
        BroadcastAction build();

    }

}
