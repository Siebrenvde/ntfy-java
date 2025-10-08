package dev.siebrenvde.ntfy.message.action;

import java.util.HashMap;
import java.util.Map;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

final class BroadcastActionImpl extends AbstractAction implements BroadcastAction {

    private final String intent;
    private final Map<String, String> extras;

    BroadcastActionImpl(final String label, final String intent, final Map<String, String> extras, final boolean clear) {
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
        return this.intent;
    }

    @Override
    public Map<String, String> extras() {
        return this.extras;
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this.label())
            .intent(this.intent)
            .extras(this.extras)
            .clear(this.clear());
    }

    static class BuilderImpl implements BroadcastAction.Builder {

        private final String label;
        private String intent = DEFAULT_INTENT;
        private Map<String, String> extras = new HashMap<>();
        private boolean clear = false;

        BuilderImpl(final String label) {
            this.label = label;
        }

        @Override
        public Builder intent(final String intent) {
            checkNotNull(intent, "intent");
            this.intent = intent;
            return this;
        }

        @Override
        public Builder extras(final Map<String, String> extras) {
            checkNotNull(extras, "extras");
            for (final Map.Entry<String, String> extra : extras.entrySet()) {
                checkNotNull(extra.getKey(), "key of extra in extras");
                checkNotNull(extra.getValue(), "value of extra in extras");
            }
            this.extras = new HashMap<>(extras);
            return this;
        }

        @Override
        public Builder setExtra(final String key, final String value) {
            checkNotNull(key, "key");
            checkNotNull(value, "value");
            this.extras.put(key, value);
            return this;
        }

        @Override
        public Builder clear(final boolean clear) {
            this.clear = clear;
            return this;
        }

        @Override
        public BroadcastAction build() {
            return new BroadcastActionImpl(this.label, this.intent, Map.copyOf(this.extras), this.clear);
        }

    }

}
