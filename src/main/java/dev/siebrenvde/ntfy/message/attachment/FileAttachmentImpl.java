package dev.siebrenvde.ntfy.message.attachment;

import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

record FileAttachmentImpl(
    Path file,
    @Nullable String fileName
) implements FileAttachment {

    FileAttachmentImpl {
        checkNotNull(file, "file");
    }

}
