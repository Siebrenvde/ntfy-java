package dev.siebrenvde.ntfy.message.action;

import dev.siebrenvde.ntfy.message.action.HttpAction.Method;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static dev.siebrenvde.ntfy.message.action.Action.http;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HttpActionTest {

    private static final String LABEL = "label";
    private static final boolean CLEAR = true;
    private static final String URL = "url";
    private static final Method METHOD = Method.DELETE;
    private static final String BODY = "body";

    private static final String HEADER_ONE_KEY = "key one";
    private static final String HEADER_ONE_VALUE = "value one";
    private static final String HEADER_TWO_KEY = "key two";
    private static final String HEADER_TWO_VALUE = "value two";
    private static final Map<String, String> HEADER_MAP_ONE = Map.of(HEADER_ONE_KEY, HEADER_ONE_VALUE);
    private static final Map<String, String> HEADER_MAP_TWO = Map.of(HEADER_ONE_KEY, HEADER_ONE_VALUE, HEADER_TWO_KEY, HEADER_TWO_VALUE);

    private static final Action ACTION = new HttpActionImpl(
        LABEL,
        URL,
        METHOD,
        HEADER_MAP_ONE,
        BODY,
        CLEAR
    );

    @Test
    void testBuilder() {
        assertEquals(
            ACTION,
            http(LABEL, URL)
                .method(METHOD)
                .headers(HEADER_MAP_ONE)
                .body(BODY)
                .clear(CLEAR)
                .build()
        );
    }

    @Test
    void testBuilderHeaders() {
        final Action one = http(LABEL, URL).headers(HEADER_MAP_ONE).build();
        assertEquals(one, http(LABEL, URL).setHeader(HEADER_ONE_KEY, HEADER_ONE_VALUE).build());

        final Action two = http(LABEL, URL).headers(HEADER_MAP_TWO).build();
        assertEquals(two, http(LABEL, URL).headers(HEADER_MAP_ONE).setHeader(HEADER_TWO_KEY, HEADER_TWO_VALUE).build());
        assertEquals(two, http(LABEL, URL).setHeader(HEADER_ONE_KEY, HEADER_ONE_VALUE).setHeader(HEADER_TWO_KEY, HEADER_TWO_VALUE).build());
        assertNotEquals(two, http(LABEL, URL).setHeader(HEADER_TWO_KEY, HEADER_TWO_VALUE).headers(HEADER_MAP_ONE).build());
    }

}
