package dev.siebrenvde.ntfy.message.action;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

abstract sealed class AbstractAction implements Action permits BroadcastActionImpl, HttpActionImpl, ViewActionImpl {

    private final String label;
    private final boolean clear;

    AbstractAction(final String label, final boolean clear) {
        checkNotNull(label, "label");
        this.label = label;
        this.clear = clear;
    }

    AbstractAction(final String label) {
        this(label, false);
    }

    @Override
    public String label() {
        return this.label;
    }

    @Override
    public boolean clear() {
        return this.clear;
    }

}
