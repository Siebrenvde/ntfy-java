package dev.siebrenvde.ntfy.message.attachment;

import org.jetbrains.annotations.Contract;

/**
 * Represents a URL attachment
 * @see <a href="https://docs.ntfy.sh/publish/#attach-file-from-a-url">Attach file from a URL</a>
 */
public interface UrlAttachment extends Attachment {

    /**
     * {@return the url}
     */
    @Contract(pure = true)
    String url();

}
