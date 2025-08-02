package dev.siebrenvde.ntfy;

public class Util {

    public static void checkArgument(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }

    private Util() {
        
    }

}
