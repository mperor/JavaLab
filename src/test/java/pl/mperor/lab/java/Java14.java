package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * Java 14 (March 2020)
 */
public class Java14 {

    // POJOs
    @Test
    public void testPairRecord() {
        var pair = Pair.of("Boy", "Girl");
        Assertions.assertEquals("Boy", pair.left);
        Assertions.assertEquals("Girl", pair.right);
        Assertions.assertEquals("Pair[left=Boy, right=Girl]", pair.toString());
        Assertions.assertEquals(Pair.of(1, 1), Pair.of(1, 1));
        Assertions.assertNotEquals(Pair.of(0, 0), Pair.of(1, 1));
        Assertions.assertThrows(NullPointerException.class, () -> Pair.of(0, null));
    }

    record Pair<L, R>(L left, R right) {
        Pair {
            Objects.requireNonNull(left);
            Objects.requireNonNull(right);
        }

        public static <L, R> Pair<L, R> of(L left, R right) {
            return new Pair<>(left, right);
        }
    }

    @Test
    public void testHelpfulNullPointerExceptions() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            String name = null;
            int length = name.length();
        });
        Assertions.assertTrue(exception.getMessage().contains("Cannot invoke \"String.length()\" because \"name\" is null"));
    }
}