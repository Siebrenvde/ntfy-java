package dev.siebrenvde.ntfy.util;

import org.jetbrains.annotations.Contract;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A result with either a value or an error
 *
 * @param <VALUE> the value type
 * @param <ERROR> the error type
 */
public sealed interface Result<VALUE, ERROR> permits ResultImpl.Success, ResultImpl.Error {

    /**
     * Creates a new success result
     *
     * @param value the value
     * @param <V> the value type
     * @param <E> the error type
     * @return a success response
     */
    @Contract(value = "_ -> new", pure = true)
    static <V, E> Result<V, E> success(final V value) {
        return new ResultImpl.Success<>(value);
    }

    /**
     * Creates a new error response
     *
     * @param error the error
     * @param <V> the value type
     * @param <E> the error type
     * @return an error response
     */
    @Contract(value = "_ -> new", pure = true)
    static <V, E> Result<V, E> error(final E error) {
        return new ResultImpl.Error<>(error);
    }

    /**
     * {@return whether the result is a success response}
     */
    @Contract(pure = true)
    boolean isSuccess();

    /**
     * {@return whether the result is an error response}
     */
    @Contract(pure = true)
    default boolean isError() {
        return !this.isSuccess();
    }

    /**
     * {@return the value if the result is a success result}
     */
    @Contract(pure = true)
    Optional<VALUE> value();

    /**
     * {@return the error if the result it an error result}
     */
    @Contract(pure = true)
    Optional<ERROR> error();

    /**
     * Returns the value or throws the provided exception if the result is an error result
     *
     * @param exceptionSupplier the exception to throw
     * @param <E> the exception type
     * @return the value
     */
    @Contract(pure = true)
    <E extends Throwable> VALUE getOrThrow(Function<String, E> exceptionSupplier) throws E;

    /**
     * Returns the value or throws an {@link IllegalStateException} if the result is an error result
     *
     * @return the value
     */
    @Contract(pure = true)
    default VALUE getOrThrow() {
        return this.getOrThrow(IllegalStateException::new);
    }

    /**
     * Executes the provided consumer if the result is a success result
     *
     * @param valueConsumer the consumer to execute
     * @return the result for chaining
     */
    @Contract(value = "_ -> this", pure = true)
    Result<VALUE, ERROR> ifSuccess(Consumer<? super VALUE> valueConsumer);

    /**
     * Executes the provided consumer if the result is an error result
     *
     * @param errorConsumer the consumer to execute
     * @return the result for chaining
     */
    @Contract(value = "_ -> this", pure = true)
    Result<VALUE, ERROR> ifError(Consumer<? super ERROR> errorConsumer);

}
