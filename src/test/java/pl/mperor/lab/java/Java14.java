package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * Java 14 (March 2020)
 */
public class Java14 {

    @Test
    public void testHelpfulNullPointerExceptions() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            String name = null;
            int length = name.length();
        });
        Assertions.assertTrue(exception.getMessage().contains("Cannot invoke \"String.length()\" because \"name\" is null"));
    }
}