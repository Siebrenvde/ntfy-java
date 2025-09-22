package dev.siebrenvde.ntfy.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

@ApiStatus.Internal
public class Util {

    public static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Instant.class, new InstantAdapter().nullSafe())
        .create();

    public static void checkNotNull(@Nullable Object object, String name) {
        if (object == null) throw new IllegalArgumentException(name + " cannot be null");
    }

    private Util() {
        
    }

}
