package dev.siebrenvde.ntfy.message.action;

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
    public boolean equals(final Object o) {
        if (!(o instanceof final ViewActionImpl that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(this.url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.url);
    }

    @Override
    public String toString() {
        return "ViewActionImpl{" +
            "url='" + this.url + '\'' +
            ", label='" + this.label() + '\'' +
            ", clear=" + this.clear() +
            '}';
    }

}
