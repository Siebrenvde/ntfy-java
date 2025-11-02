package dev.siebrenvde.ntfy.message.action;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

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

    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        final AbstractAction that = (AbstractAction) o;
        return this.clear == that.clear && Objects.equals(this.label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.label, this.clear);
    }

}
