package dev.siebrenvde.ntfy.util;

import com.google.gson.Gson;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class Util {

    public static final Gson GSON = new Gson();

    public static void checkArgument(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }

    private Util() {
        
    }

}
