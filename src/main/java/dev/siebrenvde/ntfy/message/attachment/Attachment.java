package dev.siebrenvde.ntfy.message.attachment;

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

/**
 * Represents an attachment
 *
 * @see <a href="https://docs.ntfy.sh/publish/#attachments">Attachments</a>
 */
public sealed interface Attachment permits FileAttachment, UrlAttachment {

    /**
     * Creates a new attachment from a URL and file name
     *
     * @param url the url
     * @param fileName the file name
     * @return a url attachment
     * @see <a href="https://docs.ntfy.sh/publish/#attach-file-from-a-url">Attach file from a URL</a>
     */
    static UrlAttachment url(final String url, @Nullable final String fileName) {
        return new UrlAttachmentImpl(url, fileName);
    }

    /**
     * Creates a new attachment from a URL
     *
     * @param url the url
     * @return a url attachment
     * @see <a href="https://docs.ntfy.sh/publish/#attach-file-from-a-url">Attach file from a URL</a>
     */
    static UrlAttachment url(final String url) {
        return new UrlAttachmentImpl(url, null);
    }

    /**
     * Creates a new attachment from a file and file name
     *
     * @param file the file
     * @param fileName the file name
     * @return a file attachment
     * @see <a href="https://docs.ntfy.sh/publish/#attach-local-file">Attach local file</a>
     */
    static FileAttachment file(final Path file, @Nullable final String fileName) {
        return new FileAttachmentImpl(file, fileName);
    }

    /**
     * Creates a new attachment from a file
     *
     * @param file the file
     * @return a file attachment
     * @see <a href="https://docs.ntfy.sh/publish/#attach-local-file">Attach local file</a>
     */
    static FileAttachment file(final Path file) {
        return new FileAttachmentImpl(file, null);
    }

    /**
     * {@return the file name}
     */
    @Contract(pure = true)
    @Nullable String fileName();

}
