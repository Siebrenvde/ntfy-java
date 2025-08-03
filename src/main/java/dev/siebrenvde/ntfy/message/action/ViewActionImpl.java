package dev.siebrenvde.ntfy.message.action;

import static dev.siebrenvde.ntfy.Util.checkArgument;

class ViewActionImpl extends AbstractAction implements ViewAction {

    private final String url;

    @SuppressWarnings("ConstantValue")
    ViewActionImpl(String label, String url, boolean clear) {
        super(label, clear);
        checkArgument(url != null, "url cannot be null");
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
