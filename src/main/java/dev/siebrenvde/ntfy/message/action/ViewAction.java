package dev.siebrenvde.ntfy.message.action;

import org.jetbrains.annotations.Contract;

/**
 * Represents a view action
 * @see <a href="https://docs.ntfy.sh/publish/#open-websiteapp">Open website/app</a>
 */
public interface ViewAction extends Action {

    /**
     * {@return the url to open}
     */
    @Contract(pure = true)
    String url();

}
