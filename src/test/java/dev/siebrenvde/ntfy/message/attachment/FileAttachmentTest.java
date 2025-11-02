package dev.siebrenvde.ntfy.message.attachment;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileAttachmentTest {

    private static final Path FILE = Path.of("file");
    private static final String FILE_NAME = "file name";

    @Test
    void testFileOnlyConstructor() {
        assertEquals(
            new FileAttachmentImpl(FILE, null),
            Attachment.file(FILE)
        );
    }

    @Test
    void testFileAndNameConstructor() {
        assertEquals(
            new FileAttachmentImpl(FILE, FILE_NAME),
            Attachment.file(FILE, FILE_NAME)
        );
    }

}
