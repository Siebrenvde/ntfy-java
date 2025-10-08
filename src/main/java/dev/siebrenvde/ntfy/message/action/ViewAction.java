package dev.siebrenvde.ntfy.message.action;

import org.jetbrains.annotations.Contract;

/**
 * Represents a view action
 *
 * @see <a href="https://docs.ntfy.sh/publish/#open-websiteapp">Open website/app</a>
 */
public sealed interface ViewAction extends Action permits ViewActionImpl {

    /**
     * {@return the url to open}
     */
    @Contract(pure = true)
    String url();

}
