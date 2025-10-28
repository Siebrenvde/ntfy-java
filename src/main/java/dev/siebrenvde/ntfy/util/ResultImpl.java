package dev.siebrenvde.ntfy.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static dev.siebrenvde.ntfy.internal.Util.checkNotNull;

class ResultImpl {

    record Success<VALUE, ERROR>(VALUE _value) implements Result<VALUE, ERROR> {

        Success {
            checkNotNull(_value, "value");
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public Optional<VALUE> value() {
            return Optional.of(this._value);
        }

        @Override
        public Optional<ERROR> error() {
            return Optional.empty();
        }

        @Override
        public <E extends Throwable> VALUE getOrThrow(final Function<String, E> exceptionSupplier) {
            return this._value;
        }

        @Override
        public Result<VALUE, ERROR> ifSuccess(final Consumer<? super VALUE> valueConsumer) {
            valueConsumer.accept(this._value);
            return this;
        }

        @Override
        public Result<VALUE, ERROR> ifError(final Consumer<? super ERROR> errorConsumer) {
            return this;
        }

    }

    @SuppressWarnings("JavaLangClash")
    record Error<VALUE, ERROR>(ERROR _error) implements Result<VALUE, ERROR> {

        Error {
            checkNotNull(_error, "error");
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public Optional<VALUE> value() {
            return Optional.empty();
        }

        @Override
        public Optional<ERROR> error() {
            return Optional.of(this._error);
        }

        @Override
        public <E extends Throwable> VALUE getOrThrow(final Function<String, E> exceptionSupplier) throws E {
            throw exceptionSupplier.apply("Error result does not have a value");
        }

        @Override
        public Result<VALUE, ERROR> ifSuccess(final Consumer<? super VALUE> valueConsumer) {
            return this;
        }

        @Override
        public Result<VALUE, ERROR> ifError(final Consumer<? super ERROR> errorConsumer) {
            errorConsumer.accept(this._error);
            return this;
        }

    }

}
