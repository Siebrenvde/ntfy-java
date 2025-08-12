package dev.siebrenvde.ntfy.message.attachment;

import org.jspecify.annotations.Nullable;

import static dev.siebrenvde.ntfy.Util.checkArgument;

record UrlAttachmentImpl(
    String url,
    @Nullable String fileName
) implements UrlAttachment {

    @SuppressWarnings("ConstantValue")
    UrlAttachmentImpl {
        checkArgument(url != null, "url cannot be null");
    }

}
