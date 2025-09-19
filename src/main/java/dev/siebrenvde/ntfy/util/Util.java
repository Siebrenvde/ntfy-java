package dev.siebrenvde.ntfy.util;

import com.google.gson.Gson;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

@ApiStatus.Internal
public class Util {

    public static final Gson GSON = new Gson();

    public static void checkNotNull(@Nullable Object object, String name) {
        if (object == null) throw new IllegalArgumentException(name + " cannot be null");
    }

    private Util() {
        
    }

}
