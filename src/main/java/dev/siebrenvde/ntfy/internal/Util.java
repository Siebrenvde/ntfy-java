package dev.siebrenvde.ntfy.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

@ApiStatus.Internal
public final class Util {

    public static final Gson GSON = new GsonBuilder()
        .registerTypeAdapter(Instant.class, new InstantAdapter().nullSafe())
        .create();

    @Contract("null, _ -> fail")
    public static void checkNotNull(final @Nullable Object object, final String name) {
        if (object == null) throw new IllegalArgumentException(name + " cannot be null");
    }

    @Contract("false, _ -> fail")
    public static void checkArgument(final boolean condition, final String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }

    private Util() {
        
    }

}
