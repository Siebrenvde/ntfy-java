package dev.siebrenvde.ntfy.message;

import dev.siebrenvde.ntfy.message.action.Action;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Represents a message to be published
 */
public interface Message {

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
     * {@return a list of tags}
     * @see <a href="https://docs.ntfy.sh/publish/#tags-emojis">Tags &amp; emojis</a>
     */
    @Contract(pure = true)
    @Unmodifiable List<String> tags();

    /**
     * {@return whether the body should be interpreted as Markdown}
     * @see <a href="https://docs.ntfy.sh/publish/#markdown-formatting">Markdown formatting</a>
     */
    @Contract(pure = true)
    boolean markdown();

    /**
     * {@return a list of actions}
     * @see <a href="https://docs.ntfy.sh/publish/#action-buttons">Action buttons</a>
     */
    @Contract(pure = true)
    @Unmodifiable List<Action> actions();

    /**
     * {@return the click action}
     * @see <a href="https://docs.ntfy.sh/publish/#click-action">Click action</a>
     */
    @Contract(pure = true)
    @Nullable String clickAction();

    /**
     * {@return the icon}
     * @see <a href="https://docs.ntfy.sh/publish/#icons">Icons</a>
     */
    @Contract(pure = true)
    @Nullable String icon();

    /**
     * Builder for {@link Message}
     */
    @SuppressWarnings("unused")
    interface Builder {

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
         * Sets all tags, overwriting any previously set tags
         * @param tags the tags
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#tags-emojis">Tags &amp; emojis</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder tags(List<String> tags);

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
         * Sets all actions, overwriting any previously set actions
         * @param actions the actions
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#action-buttons">Action buttons</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder actions(Action... actions);

        /**
         * Sets all actions, overwriting any previously set actions
         * @param actions the actions
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#action-buttons">Action buttons</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder actions(List<Action> actions);

        /**
         * Adds an action
         * @param action the action
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#action-buttons">Action buttons</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder addAction(Action action);

        /**
         * Sets the click action
         * @param url the url
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#click-action">Click action</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder clickAction(String url);

        /**
         * Sets the icon
         * @param url the url
         * @return the builder
         * @see <a href="https://docs.ntfy.sh/publish/#icons">Icons</a>
         */
        @Contract(value = "_ -> this", mutates = "this")
        Builder icon(String url);

        /**
         * Builds the message
         * @return a new message
         */
        @Contract(value = "-> new", pure = true)
        Message build();

    }

}
