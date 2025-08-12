package dev.siebrenvde.ntfy.response;

import dev.siebrenvde.ntfy.Util;
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

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public ErrorResponse asError() {
        return this;
    }

    @Override
    public SuccessResponse asSuccess() {
        throw new IllegalStateException("Response is not a success response");
    }

}
