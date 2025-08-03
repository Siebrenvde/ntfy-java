package dev.siebrenvde.ntfy.message.action;

import org.jetbrains.annotations.Contract;

/**
 * Represents an action button
 * @see <a href="https://docs.ntfy.sh/publish/#action-buttons">Action buttons</a>
 */
@SuppressWarnings("unused")
public interface Action {

    /**
     * Creates a new view action with a label, url and whether to clear the notification
     * @param label the label
     * @param url the url
     * @param clear whether to clear the notification after the button is tapped
     * @return a view action
     * @see <a href="https://docs.ntfy.sh/publish/#open-websiteapp">Open website/app</a>
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static ViewAction view(String label, String url, boolean clear) {
        return new ViewActionImpl(label, url, clear);
    }

    /**
     * Creates a new view action with a label and url
     * @param label the label
     * @param url the url
     * @return a view action
     * @see <a href="https://docs.ntfy.sh/publish/#open-websiteapp">Open website/app</a>
     */
    @Contract(value = "_, _ -> new", pure = true)
    static ViewAction view(String label, String url) {
        return new ViewActionImpl(label, url);
    }

    /**
     * Creates a new Android broadcast builder
     * @param label the label
     * @return a broadcast builder
     * @see <a href="https://docs.ntfy.sh/publish/#send-android-broadcast">Send Android broadcast</a>
     */
    @Contract(value = "_ -> new", pure = true)
    static BroadcastAction.Builder broadcast(String label) {
        return new BroadcastActionImpl.BuilderImpl(label);
    }

    /**
     * Creates a new Android broadcast action with a label and intent
     * @param label the label
     * @param intent the intent
     * @return a broadcast action
     * @see <a href="https://docs.ntfy.sh/publish/#send-android-broadcast">Send Android broadcast</a>
     */
    @Contract(value = "_, _ -> new", pure = true)
    static BroadcastAction broadcast(String label, String intent) {
        return broadcast(label).intent(intent).build();
    }

    /**
     * Creates a new HTTP request action builder
     * @param label the label
     * @param url the url
     * @return an http action builder
     * @see <a href="https://docs.ntfy.sh/publish/#send-http-request">Send HTTP request</a>
     */
    @Contract(value = "_, _ -> new", pure = true)
    static HttpAction.Builder http(String label, String url) {
        return new HttpActionImpl.BuilderImpl(label, url);
    }

    /**
     * {@return the action name}
     */
    @Contract(pure = true)
    String action();

    /**
     * {@return the label}
     */
    @Contract(pure = true)
    String label();

    /**
     * {@return whether to clear the notification after the button is tapped}
     */
    @Contract(pure = true)
    boolean clear();

}
