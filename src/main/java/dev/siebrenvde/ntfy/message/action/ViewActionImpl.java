package dev.siebrenvde.ntfy.message.action;

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

}
