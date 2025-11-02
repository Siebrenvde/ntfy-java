package dev.siebrenvde.ntfy.util;

import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultTest {

    private static final String SUCCESS_STRING = "success";
    private static final String ERROR_STRING = "error";

    private static final Result<String, ?> SUCCESS = Result.success(SUCCESS_STRING);
    private static final Result<?, String> ERROR = Result.error(ERROR_STRING);

    @Test
    void testSuccessIsInstanceOfSuccess() {
        assertInstanceOf(ResultImpl.Success.class, SUCCESS);
    }

    @Test
    void testErrorIsInstanceOfError() {
        assertInstanceOf(ResultImpl.Error.class, ERROR);
    }

    @Test
    void testSuccessIsSuccess() {
        assertTrue(SUCCESS.isSuccess());
    }

    @Test
    void testErrorIsNotSuccess() {
        assertFalse(ERROR.isSuccess());
    }

    @Test
    void testSuccessIsNotError() {
        assertFalse(SUCCESS.isError());
    }

    @Test
    void testErrorIsError() {
        assertTrue(ERROR.isError());
    }

    @Test
    void testSuccessValueReturnsOptionalValue() {
        assertTrue(SUCCESS.value().isPresent());
        assertEquals(SUCCESS_STRING, SUCCESS.value().get());
    }

    @Test
    void testErrorValueReturnsEmptyOptional() {
        assertTrue(ERROR.value().isEmpty());
    }

    @Test
    void testSuccessErrorReturnsEmptyOptional() {
        assertTrue(SUCCESS.error().isEmpty());
    }

    @Test
    void testErrorErrorReturnsOptionalError() {
        assertTrue(ERROR.error().isPresent());
        assertEquals(ERROR_STRING, ERROR.error().get());
    }

    @Test
    void testSuccessGetOrThrowReturnsValue() {
        assertEquals(SUCCESS_STRING, SUCCESS.getOrThrow());
    }

    @Test
    void testErrorGetOrThrowThrowsProvidedException() {
        assertThrows(TestException.class, () -> ERROR.getOrThrow(TestException::new));
    }

    @Test
    void testErrorGetOrThrowThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, ERROR::getOrThrow);
    }

    @Test
    void testSuccessIfSuccessExecutes() {
        final AtomicBoolean hasRun = new AtomicBoolean();
        SUCCESS.ifSuccess(v -> hasRun.set(true));
        assertTrue(hasRun.get());
    }

    @Test
    void testErrorIfSuccessDoesNotExecute() {
        final AtomicBoolean hasRun = new AtomicBoolean();
        ERROR.ifSuccess(v -> hasRun.set(true));
        assertFalse(hasRun.get());
    }

    @Test
    void testSuccessIfErrorDoesNotExecute() {
        final AtomicBoolean hasRun = new AtomicBoolean();
        SUCCESS.ifError(v -> hasRun.set(true));
        assertFalse(hasRun.get());
    }

    @Test
    void testErrorIfErrorExecutes() {
        final AtomicBoolean hasRun = new AtomicBoolean();
        ERROR.ifError(v -> hasRun.set(true));
        assertTrue(hasRun.get());
    }

    private static class TestException extends Exception {

        @Serial
        private static final long serialVersionUID = 1L;

        TestException(final String message) {
            super(message);
        }

    }

}
