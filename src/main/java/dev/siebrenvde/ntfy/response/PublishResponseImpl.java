package dev.siebrenvde.ntfy.response;

import com.google.gson.JsonObject;
import dev.siebrenvde.ntfy.internal.Util;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

record PublishResponseImpl(
    String id,
    Instant time,
    @Nullable Instant expires
) implements PublishResponse {

    static PublishResponseImpl fromJson(String json) {
        JsonObject object = Util.GSON.fromJson(json, JsonObject.class);
        return new PublishResponseImpl(
            object.get("id").getAsString(),
            Instant.ofEpochSecond(object.get("time").getAsLong()),
            object.has("expires")
                ? Instant.ofEpochSecond(object.get("expires").getAsLong())
                : null
        );
    }

}
