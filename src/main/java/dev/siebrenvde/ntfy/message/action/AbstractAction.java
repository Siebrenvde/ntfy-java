package dev.siebrenvde.ntfy.message.action;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

abstract class AbstractAction implements Action {

    private final String label;
    private final boolean clear;

    AbstractAction(String label, boolean clear) {
        checkNotNull(label, "label");
        this.label = label;
        this.clear = clear;
    }

    AbstractAction(String label) {
        this(label, false);
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public boolean clear() {
        return clear;
    }

}
