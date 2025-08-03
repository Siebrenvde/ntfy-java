package dev.siebrenvde.ntfy.message;

import dev.siebrenvde.ntfy.message.action.Action;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dev.siebrenvde.ntfy.Util.checkArgument;

record MessageImpl(
    @Nullable String body,
    @Nullable String title,
    Priority priority,
    List<String> tags,
    boolean markdown,
    List<Action> actions,
    @Nullable String clickAction
) implements Message {

    @SuppressWarnings("ConstantValue")
    static class BuilderImpl implements Message.Builder {

        private @Nullable String body;
        private @Nullable String title;
        private Priority priority = Priority.DEFAULT;
        private List<String> tags = new ArrayList<>();
        private boolean markdown = false;
        private List<Action> actions = new ArrayList<>();
        private @Nullable String clickAction;

        @Override
        public Builder body(String body) {
            this.body = body;
            return this;
        }

        @Override
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        @Override
        public Builder priority(Priority priority) {
            checkArgument(priority != null, "priority cannot be null");
            this.priority = priority;
            return this;
        }

        @Override
        public Builder tags(String... tags) {
            checkArgument(tags != null, "tags cannot be null");
            this.tags = new ArrayList<>(List.of(tags));
            return this;
        }

        @Override
        public Builder tags(List<String> tags) {
            this.tags = new ArrayList<>(tags);
            return this;
        }

        @Override
        public Builder addTag(String tag) {
            checkArgument(tag != null, "tag cannot be null");
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
            checkArgument(actions != null, "actions cannot be null");
            this.actions = new ArrayList<>(List.of(actions));
            return this;
        }

        @Override
        public Builder actions(List<Action> actions) {
            checkArgument(actions != null, "actions cannot be null");
            this.actions = new ArrayList<>(actions);
            return this;
        }

        @Override
        public Builder addAction(Action action) {
            checkArgument(action != null, "action cannot be null");
            this.actions.add(action);
            return this;
        }

        @Override
        public Builder clickAction(String url) {
            this.clickAction = url;
            return this;
        }

        @Override
        public Message build() {
            return new MessageImpl(
                body,
                title,
                priority,
                Collections.unmodifiableList(tags),
                markdown,
                Collections.unmodifiableList(actions),
                clickAction
            );
        }

    }

}
