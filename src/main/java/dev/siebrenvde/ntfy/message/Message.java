package dev.siebrenvde.ntfy.message;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

/**
 * Represents a message to be published
 */
public sealed interface Message permits MessageImpl {

    /**
     * Creates a new message builder
     * @return a message builder
     */
    @Contract(value = "-> new", pure = true)
    static Builder message() {
        return new MessageImpl.BuilderImpl();
    }

    /**
     * Creates a new message with only a body
     * @param body the body
     * @return a message
     */
    @Contract(value = "_ -> new", pure = true)
    static Message message(String body) {
        return message().body(body).build();
    }

    /**
     * Creates a new message with a body and title
     * @param body the body
     * @param title the title
     * @return a message
     */
    @Contract(value = "_, _ -> new", pure = true)
    static Message message(String body, String title) {
        return message().body(body).title(title).build();
    }

    /**
     * {@return the message body}
     */
    @Contract(pure = true)
    @Nullable String body();

    /**
     * {@return the message title}
     */
    @Contract(pure = true)
    @Nullable String title();

    /**
     * {@return the priority}
     * @see <a href="https://docs.ntfy.sh/publish/#message-priority">Message priority</a>
     */
    @Contract(pure = true)
    Priority priority();

    /**
     * {@return an array of tags}
     * @see <a href="https://docs.ntfy.sh/publish/#tags-emojis">Tags &amp; emojis</a>
     */
    @Contract(pure = true)
    String[] tags();

    /**
     * {@return whether the body should be interpreted as Markdown}
     * @see <a href="https://docs.ntfy.sh/publish/#markdown-formatting">Markdown formatting</a>
     */
    @Contract(pure = true)
    boolean markdown();

    /**
     * An enum representing the available priority levels
     * @see <a href="https://docs.ntfy.sh/publish/#message-priority">Message priority</a>
     */
    enum Priority {
        MAX,
        HIGH,
        DEFAULT,
        LOW,
        MIN
    }

    /**
     * Builder for {@link Message}
     */
    @SuppressWarnings("unused")
    sealed interface Builder permits MessageImpl.BuilderImpl {

        /**
         * Sets the message body
         * @param body the body
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder body(String body);

        /**
         * Sets the message title
         * @param title the title
         * @return the builder
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder title(String title);

        /**
         * Sets the priority
         * @param priority the priority
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#message-priority">Message priority</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder priority(Priority priority);

        /**
         * Sets all tags, overwriting any previously set tags
         * @param tags the tags
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#tags-emojis">Tags &amp; emojis</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder tags(String... tags);

        /**
         * Adds a tag
         * @param tag the tag
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#tags-emojis">Tags &amp; emojis</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder addTag(String tag);

        /**
         * Sets whether the body should be interpreted as Markdown
         * @param markdown <code>true</code> if the body should be interpreted as Markdown
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#markdown-formatting">Markdown formatting</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder markdown(boolean markdown);

        /**
         * Builds the message
         * @return a new message
         */
        @Contract(value = "-> new", pure = true)
        Message build();

    }

}
