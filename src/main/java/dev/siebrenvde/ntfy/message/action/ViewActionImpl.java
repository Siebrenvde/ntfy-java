package dev.siebrenvde.ntfy.message.action;

import static dev.siebrenvde.ntfy.util.Util.checkNotNull;

class ViewActionImpl extends AbstractAction implements ViewAction {

    private final String url;

    ViewActionImpl(String label, String url, boolean clear) {
        super(label, clear);
        checkNotNull(url, "url");
        this.url = url;
    }

    ViewActionImpl(String label, String url) {
        super(label);
        this.url = url;
    }

    @Override
    public String action() {
        return "view";
    }

    @Override
    public String url() {
        return url;
    }

}
