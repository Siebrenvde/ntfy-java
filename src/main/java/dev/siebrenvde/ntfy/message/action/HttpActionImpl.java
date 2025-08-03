package dev.siebrenvde.ntfy.message.action;

import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static dev.siebrenvde.ntfy.Util.checkArgument;

class HttpActionImpl extends AbstractAction implements HttpAction {

    private final String url;
    private final Method method;
    private final Map<String, String> headers;
    private final @Nullable String body;

    HttpActionImpl(String label, String url, Method method, Map<String, String> headers, @Nullable String body, boolean clear) {
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
        return url;
    }

    @Override
    public Method method() {
        return method;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public @Nullable String body() {
        return body;
    }

    @SuppressWarnings("ConstantValue")
    static class BuilderImpl implements HttpAction.Builder {

        private final String label;
        private final String url;
        private Method method = DEFAULT_METHOD;
        private Map<String, String> headers = new HashMap<>();
        private @Nullable String body;
        private boolean clear = false;

        BuilderImpl(String label, String url) {
            checkArgument(url != null, "url cannot be null");
            this.label = label;
            this.url = url;
        }

        @Override
        public Builder method(Method method) {
            checkArgument(method != null, "method cannot be null");
            this.method = method;
            return this;
        }

        @Override
        public Builder headers(Map<String, String> headers) {
            checkArgument(headers != null, "headers cannot be null");
            this.headers = headers;
            return this;
        }

        @Override
        public Builder setHeader(String header, String value) {
            checkArgument(header != null, "header cannot be null");
            checkArgument(value != null, "value cannot be null");
            this.headers.put(header, value);
            return this;
        }

        @Override
        public Builder body(String body) {
            this.body = body;
            return this;
        }

        @Override
        public Builder clear(boolean clear) {
            this.clear = clear;
            return this;
        }

        @Override
        public HttpAction build() {
            return new HttpActionImpl(label, url, method, headers, body, clear);
        }

    }

}
