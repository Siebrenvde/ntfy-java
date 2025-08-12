package dev.siebrenvde.ntfy.message.attachment;

import org.jspecify.annotations.Nullable;

import java.nio.file.Path;

import static dev.siebrenvde.ntfy.util.Util.checkArgument;

record FileAttachmentImpl(
    Path file,
    @Nullable String fileName
) implements FileAttachment {

    @SuppressWarnings("ConstantValue")
    FileAttachmentImpl {
        checkArgument(file != null, "file cannot be null");
    }

}
