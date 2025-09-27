package dev.siebrenvde.ntfy.message.action;

import java.util.HashMap;
import java.util.Map;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

final class BroadcastActionImpl extends AbstractAction implements BroadcastAction {

    private final String intent;
    private final Map<String, String> extras;

    BroadcastActionImpl(String label, String intent, Map<String, String> extras, boolean clear) {
        super(label, clear);
        this.intent = intent;
        this.extras = extras;
    }

    @Override
    public String action() {
        return "broadcast";
    }

    @Override
    public String intent() {
        return intent;
    }

    @Override
    public Map<String, String> extras() {
        return extras;
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(label())
            .intent(intent)
            .extras(extras)
            .clear(clear());
    }

    static class BuilderImpl implements BroadcastAction.Builder {

        private final String label;
        private String intent = DEFAULT_INTENT;
        private Map<String, String> extras = new HashMap<>();
        private boolean clear = false;

        BuilderImpl(String label) {
            this.label = label;
        }

        @Override
        public Builder intent(String intent) {
            checkNotNull(intent, "intent");
            this.intent = intent;
            return this;
        }

        @Override
        public Builder extras(Map<String, String> extras) {
            checkNotNull(extras, "extras");
            this.extras = new HashMap<>(extras);
            return this;
        }

        @Override
        public Builder setExtra(String key, String value) {
            checkNotNull(key, "key");
            checkNotNull(value, "value");
            this.extras.put(key, value);
            return this;
        }

        @Override
        public Builder clear(boolean clear) {
            this.clear = clear;
            return this;
        }

        @Override
        public BroadcastAction build() {
            return new BroadcastActionImpl(label, intent, Map.copyOf(extras), clear);
        }

    }

}
