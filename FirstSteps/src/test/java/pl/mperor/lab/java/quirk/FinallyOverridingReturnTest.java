package pl.mperor.lab.java.quirk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FinallyOverridingReturnTest {

    @Test
    public void testReturnInFinallyOverridesTryReturn() {
        Assertions.assertEquals("finally", returnFromTryThenFinally(),
                "a return in finally replaces the value already queued by try's return");
    }

    private static String returnFromTryThenFinally() {
        try {
            return "try";
        } finally {
            return "finally";
        }
    }

    @Test
    public void testReturnInFinallySwallowsException() {
        Assertions.assertEquals("finally", returnFromFinallyAfterThrow(),
                "a return in finally discards an exception in flight, so the caller never sees it");
    }

    private static String returnFromFinallyAfterThrow() {
        try {
            throw new IllegalStateException("boom");
        } finally {
            return "finally";
        }
    }

    @Test
    public void testFinallyMutatingLocalPrimitiveDoesNotChangeAlreadyCapturedReturnValue() {
        Assertions.assertEquals(1, returnPrimitiveThenMutateInFinally(),
                "try's return expression is evaluated and captured before finally runs, so later reassigning the local has no effect");
    }

    private static int returnPrimitiveThenMutateInFinally() {
        int count = 1;
        try {
            return count;
        } finally {
            count = 2;
        }
    }

    @Test
    public void testFinallyMutatingReturnedObjectIsVisibleToCaller() {
        Assertions.assertEquals(List.of("Ann", "Bob"), returnListThenMutateInFinally(),
                "unlike a primitive, the captured return value is a reference, so mutating the same object in finally is visible to the caller");
    }

    private static List<String> returnListThenMutateInFinally() {
        List<String> names = new ArrayList<>();
        names.add("Ann");
        try {
            return names;
        } finally {
            names.add("Bob");
        }
    }

    @Test
    public void testTryWithResourcesPreservesPrimaryExceptionAndSuppressesCloseFailure() {
        var exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            try (var resource = new FailingResource()) {
                throw new IllegalStateException("primary failure");
            }
        }, "unlike finally, try-with-resources lets the primary exception from the body propagate");

        Assertions.assertEquals("primary failure", exception.getMessage());
        Assertions.assertEquals(1, exception.getSuppressed().length,
                "close()'s exception is attached as suppressed instead of replacing the primary one");
        Assertions.assertEquals("close failure", exception.getSuppressed()[0].getMessage());
    }

    private static final class FailingResource implements AutoCloseable {
        @Override
        public void close() {
            throw new IllegalStateException("close failure");
        }
    }
}
