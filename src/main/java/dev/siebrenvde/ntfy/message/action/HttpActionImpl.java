package dev.siebrenvde.ntfy.message.action;

import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

final class HttpActionImpl extends AbstractAction implements HttpAction {

    private final String url;
    private final Method method;
    private final Map<String, String> headers;
    private final @Nullable String body;

    HttpActionImpl(final String label, final String url, final Method method, final Map<String, String> headers, @Nullable final String body, final boolean clear) {
        super(label, clear);
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public String action() {
        return "http";
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public Method method() {
        return this.method;
    }

    @Override
    public Map<String, String> headers() {
        return this.headers;
    }

    @Override
    public @Nullable String body() {
        return this.body;
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this.label(), this.url)
            .method(this.method)
            .headers(this.headers)
            .body(this.body)
            .clear(this.clear());
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final HttpActionImpl that = (HttpActionImpl) o;
        return Objects.equals(this.url, that.url) && this.method == that.method && Objects.equals(this.headers, that.headers) && Objects.equals(this.body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.url, this.method, this.headers, this.body);
    }

    static class BuilderImpl implements HttpAction.Builder {

        private final String label;
        private final String url;
        private Method method = DEFAULT_METHOD;
        private Map<String, String> headers = new HashMap<>();
        private @Nullable String body;
        private boolean clear = false;

        BuilderImpl(final String label, final String url) {
            checkNotNull(url, "url");
            this.label = label;
            this.url = url;
        }

        @Override
        public Builder method(final Method method) {
            checkNotNull(method, "method");
            this.method = method;
            return this;
        }

        @Override
        public Builder headers(final Map<String, String> headers) {
            checkNotNull(headers, "headers");
            for (final Map.Entry<String, String> header : headers.entrySet()) {
                checkNotNull(header.getKey(), "key of header in headers");
                checkNotNull(header.getValue(), "value of header in headers");
            }
            this.headers = new HashMap<>(headers);
            return this;
        }

        @Override
        public Builder setHeader(final String header, final String value) {
            checkNotNull(header, "header");
            checkNotNull(value, "value");
            this.headers.put(header, value);
            return this;
        }

        @Override
        public Builder body(@Nullable final String body) {
            this.body = body;
            return this;
        }

        @Override
        public Builder clear(final boolean clear) {
            this.clear = clear;
            return this;
        }

        @Override
        public HttpAction build() {
            return new HttpActionImpl(this.label, this.url, this.method, Map.copyOf(this.headers), this.body, this.clear);
        }

    }

}
