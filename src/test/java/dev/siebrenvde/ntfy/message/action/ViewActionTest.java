package dev.siebrenvde.ntfy.message.action;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViewActionTest {

    private static final String LABEL = "label";
    private static final boolean CLEAR = true;
    private static final String URL = "url";

    @Test
    void testLabelAndUrlConstructor() {
        assertEquals(
            new ViewActionImpl(LABEL, URL),
            Action.view(LABEL, URL)
        );
    }

    @Test
    void testFullConstructor() {
        assertEquals(
            new ViewActionImpl(LABEL, URL, CLEAR),
            Action.view(LABEL, URL, CLEAR)
        );
    }

}
