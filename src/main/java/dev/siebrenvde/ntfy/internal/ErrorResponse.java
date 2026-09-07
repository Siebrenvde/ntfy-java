package dev.siebrenvde.ntfy.internal;

import org.jspecify.annotations.Nullable;

public record ErrorResponse(
    int code,
    int http,
    String error,
    @Nullable String link
) {

    public static ErrorResponse fromJson(final String json) {
        return Util.GSON.fromJson(json, ErrorResponse.class);
    }

}
