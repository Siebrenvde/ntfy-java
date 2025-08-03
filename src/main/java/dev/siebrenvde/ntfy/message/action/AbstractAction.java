package dev.siebrenvde.ntfy.message.action;

import static dev.siebrenvde.ntfy.Util.checkArgument;

abstract class AbstractAction implements Action {

    private final String label;
    private final boolean clear;

    @SuppressWarnings("ConstantValue")
    AbstractAction(String label, boolean clear) {
        checkArgument(label != null, "label cannot be null");
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
