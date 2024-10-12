package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Java 10 (March 2018)
 */
public class Java10 {

    @Test
    public void testLocalVariableTypeInferenceKeywordAkaVar() {
        var message = "Hello Java 10!";
        // same as String message = ...
        Assertions.assertEquals("Hello Java 10!", message);

        var number = 10;
        // same as int number = ...
        Assertions.assertEquals(10, number);
    }

    @Test
    public void testUnmodifiableCollectionCopyOf() {
        var original = List.of("Java", "Python", "JavaScript");
        var copy = List.copyOf(original);
        Assertions.assertEquals(original, copy);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> copy.add("Ruby"));
    }

    @Test
    public void testOptionalEnhancements() {
        Optional<String> optional = Optional.empty();
        Assertions.assertThrows(IllegalStateException.class, () -> optional.orElseThrow(IllegalStateException::new));
        Assertions.assertEquals("", optional.stream().collect(Collectors.joining()));
    }

    @Test
    public void testCompletableFutureTimeout() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Result";
        }).orTimeout(10, TimeUnit.MILLISECONDS);

        Assertions.assertThrows(Exception.class, future::join);
    }
}
