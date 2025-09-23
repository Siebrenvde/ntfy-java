package dev.siebrenvde.ntfy.message.attachment;

import org.jetbrains.annotations.Contract;

import java.nio.file.Path;

/**
 * Represents a file attachment
 * @see <a href="https://docs.ntfy.sh/publish/#attach-local-file">Attach local file</a>
 */
public sealed interface FileAttachment extends Attachment permits FileAttachmentImpl {

    /**
     * {@return the file}
     */
    @Contract(pure = true)
    Path file();

}
