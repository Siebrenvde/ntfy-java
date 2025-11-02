package dev.siebrenvde.ntfy.message.attachment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlAttachmentTest {

    private static final String URL = "url";
    private static final String FILE_NAME = "file name";

    @Test
    void testUrlOnlyConstructor() {
        assertEquals(
            new UrlAttachmentImpl(URL, null),
            Attachment.url(URL)
        );
    }

    @Test
    void testUrlAndNameConstructor() {
        assertEquals(
            new UrlAttachmentImpl(URL, FILE_NAME),
            Attachment.url(URL, FILE_NAME)
        );
    }

}
