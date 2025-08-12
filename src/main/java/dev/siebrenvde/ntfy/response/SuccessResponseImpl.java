package dev.siebrenvde.ntfy.response;

import com.google.gson.JsonObject;
import dev.siebrenvde.ntfy.util.Util;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

record SuccessResponseImpl(
    String id,
    Instant time,
    @Nullable Instant expires
) implements SuccessResponse {

    static SuccessResponseImpl fromJson(String json) {
        JsonObject object = Util.GSON.fromJson(json, JsonObject.class);
        return new SuccessResponseImpl(
            object.get("id").getAsString(),
            Instant.ofEpochSecond(object.get("time").getAsLong()),
            object.has("expires")
                ? Instant.ofEpochSecond(object.get("expires").getAsLong())
                : null
        );
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public SuccessResponse asSuccess() {
        return this;
    }

    @Override
    public ErrorResponse asError() {
        throw new IllegalStateException("Response is not an error response");
    }

}
