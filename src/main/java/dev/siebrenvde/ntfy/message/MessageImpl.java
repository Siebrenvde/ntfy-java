package dev.siebrenvde.ntfy.message;

import dev.siebrenvde.ntfy.message.action.Action;
import dev.siebrenvde.ntfy.message.attachment.Attachment;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

record MessageImpl(
    @Nullable String body,
    @Nullable String title,
    Priority priority,
    List<String> tags,
    boolean markdown,
    List<Action> actions,
    @Nullable String clickAction,
    @Nullable Attachment attachment,
    @Nullable String icon,
    @Nullable String email,
    @Nullable String phone,
    boolean cache,
    boolean firebase
) implements Message {

    @Override
    public Builder toBuilder() {
        return new BuilderImpl()
            .body(this.body)
            .title(this.title)
            .priority(this.priority)
            .tags(this.tags)
            .markdown(this.markdown)
            .actions(this.actions)
            .clickAction(this.clickAction)
            .attachment(this.attachment)
            .icon(this.icon)
            .email(this.email)
            .phone(this.phone)
            .cache(this.cache)
            .firebase(this.firebase);
    }

    static final class BuilderImpl implements Message.Builder {

        private @Nullable String body;
        private @Nullable String title;
        private Priority priority = Priority.DEFAULT;
        private List<String> tags = new ArrayList<>();
        private boolean markdown = false;
        private List<Action> actions = new ArrayList<>();
        private @Nullable String clickAction;
        private @Nullable Attachment attachment;
        private @Nullable String icon;
        private @Nullable String email;
        private @Nullable String phone;
        private boolean cache = true;
        private boolean firebase = true;

        @Override
        public Builder body(@Nullable final String body) {
            this.body = body;
            return this;
        }

        @Override
        public Builder title(@Nullable final String title) {
            this.title = title;
            return this;
        }

        @Override
        public Builder priority(final Priority priority) {
            checkNotNull(priority, "priority");
            this.priority = priority;
            return this;
        }

        @Override
        public Builder tags(final String... tags) {
            checkNotNull(tags, "tags");
            for (final String tag : tags) checkNotNull(tag, "tag in tags");
            this.tags = new ArrayList<>(List.of(tags));
            return this;
        }

        @Override
        public Builder tags(final List<String> tags) {
            checkNotNull(tags, "tags");
            for (final String tag : tags) checkNotNull(tag, "tag in tags");
            this.tags = new ArrayList<>(tags);
            return this;
        }

        @Override
        public Builder addTag(final String tag) {
            checkNotNull(tag, "tag");
            this.tags.add(tag);
            return this;
        }

        @Override
        public Builder markdown(final boolean markdown) {
            this.markdown = markdown;
            return this;
        }

        @Override
        public Builder actions(final Action... actions) {
            checkNotNull(actions, "actions");
            for (final Action action : actions) checkNotNull(action, "action in actions");
            this.actions = new ArrayList<>(List.of(actions));
            return this;
        }

        @Override
        public Builder actions(final List<Action> actions) {
            checkNotNull(actions, "actions");
            for (final Action action : actions) checkNotNull(action, "action in actions");
            this.actions = new ArrayList<>(actions);
            return this;
        }

        @Override
        public Builder addAction(final Action action) {
            checkNotNull(action, "action");
            this.actions.add(action);
            return this;
        }

        @Override
        public Builder clickAction(@Nullable final String url) {
            this.clickAction = url;
            return this;
        }

        @Override
        public Builder attachment(@Nullable final Attachment attachment) {
            this.attachment = attachment;
            return this;
        }

        @Override
        public Builder icon(@Nullable final String url) {
            this.icon = url;
            return this;
        }

        @Override
        public Builder email(@Nullable final String email) {
            this.email = email;
            return this;
        }

        @Override
        public Builder phone(@Nullable final String phone) {
            this.phone = phone;
            return this;
        }

        @Override
        public Builder cache(final boolean cache) {
            this.cache = cache;
            return this;
        }

        @Override
        public Builder firebase(final boolean firebase) {
            this.firebase = firebase;
            return this;
        }

        @Override
        public Message build() {
            return new MessageImpl(
                this.body,
                this.title,
                this.priority,
                List.copyOf(this.tags),
                this.markdown,
                List.copyOf(this.actions),
                this.clickAction,
                this.attachment,
                this.icon,
                this.email,
                this.phone,
                this.cache,
                this.firebase
            );
        }

    }

}
