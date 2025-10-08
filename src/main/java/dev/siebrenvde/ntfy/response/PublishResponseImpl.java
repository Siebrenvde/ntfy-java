package dev.siebrenvde.ntfy.response;

import dev.siebrenvde.ntfy.internal.Util;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

record PublishResponseImpl(
    String id,
    Instant time,
    @Nullable Instant expires
) implements PublishResponse {

    static PublishResponseImpl fromJson(final String json) {
        return Util.GSON.fromJson(json, PublishResponseImpl.class);
    }

}
