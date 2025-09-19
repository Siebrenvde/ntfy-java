package dev.siebrenvde.ntfy.message.attachment;

import org.jspecify.annotations.Nullable;

import static dev.siebrenvde.ntfy.util.Util.checkNotNull;

record UrlAttachmentImpl(
    String url,
    @Nullable String fileName
) implements UrlAttachment {

    UrlAttachmentImpl {
        checkNotNull(url, "url");
    }

}
