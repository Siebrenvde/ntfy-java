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
            .body(body)
            .title(title)
            .priority(priority)
            .tags(tags)
            .markdown(markdown)
            .actions(actions)
            .clickAction(clickAction)
            .attachment(attachment)
            .icon(icon)
            .email(email)
            .phone(phone)
            .cache(cache)
            .firebase(firebase);
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
        public Builder body(@Nullable String body) {
            this.body = body;
            return this;
        }

        @Override
        public Builder title(@Nullable String title) {
            this.title = title;
            return this;
        }

        @Override
        public Builder priority(Priority priority) {
            checkNotNull(priority, "priority");
            this.priority = priority;
            return this;
        }

        @Override
        public Builder tags(String... tags) {
            checkNotNull(tags, "tags");
            for (String tag : tags) checkNotNull(tag, "tag in tags");
            this.tags = new ArrayList<>(List.of(tags));
            return this;
        }

        @Override
        public Builder tags(List<String> tags) {
            checkNotNull(tags, "tags");
            for (String tag : tags) checkNotNull(tag, "tag in tags");
            this.tags = new ArrayList<>(tags);
            return this;
        }

        @Override
        public Builder addTag(String tag) {
            checkNotNull(tag, "tag");
            this.tags.add(tag);
            return this;
        }

        @Override
        public Builder markdown(boolean markdown) {
            this.markdown = true;
            return this;
        }

        @Override
        public Builder actions(Action... actions) {
            checkNotNull(actions, "actions");
            for (Action action : actions) checkNotNull(action, "action in actions");
            this.actions = new ArrayList<>(List.of(actions));
            return this;
        }

        @Override
        public Builder actions(List<Action> actions) {
            checkNotNull(actions, "actions");
            for (Action action : actions) checkNotNull(action, "action in actions");
            this.actions = new ArrayList<>(actions);
            return this;
        }

        @Override
        public Builder addAction(Action action) {
            checkNotNull(action, "action");
            this.actions.add(action);
            return this;
        }

        @Override
        public Builder clickAction(@Nullable String url) {
            this.clickAction = url;
            return this;
        }

        @Override
        public Builder attachment(@Nullable Attachment attachment) {
            this.attachment = attachment;
            return this;
        }

        @Override
        public Builder icon(@Nullable String url) {
            this.icon = url;
            return this;
        }

        @Override
        public Builder email(@Nullable String email) {
            this.email = email;
            return this;
        }

        @Override
        public Builder phone(@Nullable String phone) {
            this.phone = phone;
            return this;
        }

        @Override
        public Builder cache(boolean cache) {
            this.cache = cache;
            return this;
        }

        @Override
        public Builder firebase(boolean firebase) {
            this.firebase = firebase;
            return this;
        }

        @Override
        public Message build() {
            return new MessageImpl(
                body,
                title,
                priority,
                List.copyOf(tags),
                markdown,
                List.copyOf(actions),
                clickAction,
                attachment,
                icon,
                email,
                phone,
                cache,
                firebase
            );
        }

    }

}
