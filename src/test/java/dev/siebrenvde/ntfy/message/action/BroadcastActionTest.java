package dev.siebrenvde.ntfy.message.action;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static dev.siebrenvde.ntfy.message.action.Action.broadcast;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BroadcastActionTest {

    private static final String LABEL = "label";
    private static final boolean CLEAR = true;
    private static final String INTENT = "intent";

    private static final String EXTRA_ONE_KEY = "key one";
    private static final String EXTRA_ONE_VALUE = "value one";
    private static final String EXTRA_TWO_KEY = "key two";
    private static final String EXTRA_TWO_VALUE = "value two";
    private static final Map<String, String> EXTRA_MAP_ONE = Map.of(EXTRA_ONE_KEY, EXTRA_ONE_VALUE);
    private static final Map<String, String> EXTRA_MAP_TWO = Map.of(EXTRA_ONE_KEY, EXTRA_ONE_VALUE, EXTRA_TWO_KEY, EXTRA_TWO_VALUE);

    private static final Action ACTION = new BroadcastActionImpl(
        LABEL,
        INTENT,
        EXTRA_MAP_ONE,
        CLEAR
    );

    @Test
    void testConstructor() {
        assertEquals(
            new BroadcastActionImpl(LABEL, INTENT, Map.of(), false),
            broadcast(LABEL, INTENT)
        );
    }

    @Test
    void testBuilder() {
        assertEquals(
            ACTION,
            broadcast(LABEL)
                .intent(INTENT)
                .extras(EXTRA_MAP_ONE)
                .clear(CLEAR)
                .build()
        );
    }

    @Test
    void testBuilderExtras() {
        final Action one = broadcast(LABEL).extras(EXTRA_MAP_ONE).build();
        assertEquals(one, broadcast(LABEL).setExtra(EXTRA_ONE_KEY, EXTRA_ONE_VALUE).build());

        final Action two = broadcast(LABEL).extras(EXTRA_MAP_TWO).build();
        assertEquals(two, broadcast(LABEL).extras(EXTRA_MAP_ONE).setExtra(EXTRA_TWO_KEY, EXTRA_TWO_VALUE).build());
        assertEquals(two, broadcast(LABEL).setExtra(EXTRA_ONE_KEY, EXTRA_ONE_VALUE).setExtra(EXTRA_TWO_KEY, EXTRA_TWO_VALUE).build());
        assertNotEquals(two, broadcast(LABEL).setExtra(EXTRA_TWO_KEY, EXTRA_TWO_VALUE).extras(EXTRA_MAP_ONE).build());
    }

}
