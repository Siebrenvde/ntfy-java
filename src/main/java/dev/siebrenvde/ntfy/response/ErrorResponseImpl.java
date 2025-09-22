package dev.siebrenvde.ntfy.response;

import dev.siebrenvde.ntfy.internal.Util;
import org.jspecify.annotations.Nullable;

record ErrorResponseImpl(
    int code,
    int http,
    String error,
    @Nullable String link
) implements ErrorResponse {

    static ErrorResponseImpl fromJson(String json) {
        return Util.GSON.fromJson(json, ErrorResponseImpl.class);
    }

}
