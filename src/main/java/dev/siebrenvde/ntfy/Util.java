package dev.siebrenvde.ntfy;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class Util {

    public static void checkArgument(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }

    private Util() {
        
    }

}
