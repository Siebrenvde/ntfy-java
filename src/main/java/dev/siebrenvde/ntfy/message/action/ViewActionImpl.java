package dev.siebrenvde.ntfy.message.action;

import org.jspecify.annotations.Nullable;

import java.util.Objects;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

final class ViewActionImpl extends AbstractAction implements ViewAction {

    private final String url;

    ViewActionImpl(final String label, final String url, final boolean clear) {
        super(label, clear);
        checkNotNull(url, "url");
        this.url = url;
    }

    ViewActionImpl(final String label, final String url) {
        super(label);
        this.url = url;
    }

    @Override
    public String action() {
        return "view";
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final ViewActionImpl that = (ViewActionImpl) o;
        return Objects.equals(this.url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.url);
    }

}
