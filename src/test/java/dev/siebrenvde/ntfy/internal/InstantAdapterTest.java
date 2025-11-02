package dev.siebrenvde.ntfy.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstantAdapterTest {

    private static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Instant.class, new InstantAdapter().nullSafe())
        .create();

    private static final long TIME_SECONDS = 1069977600;
    private static final Instant TIME_INSTANT = Instant.ofEpochSecond(TIME_SECONDS);

    @Test
    void testRead() {
        final Instant parsed = GSON.fromJson(String.valueOf(TIME_SECONDS), Instant.class);
        assertEquals(TIME_INSTANT, parsed);
    }

    @Test
    void testWrite() {
        final String json = GSON.toJson(TIME_INSTANT);
        assertEquals(String.valueOf(TIME_SECONDS), json);
    }

}
