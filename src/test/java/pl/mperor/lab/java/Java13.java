package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Java 13 (September 2019)
 */
public class Java13 {

    @Test
    public void testTextBlock() {
        String json = """
                {
                    "login": "admin",
                    "password": "*****"
                }
                """;

        Assertions.assertTrue(json.contains("\"login\": \"admin\""));
    }

    @Test
    public void testStringFormatted() {
        Assertions.assertEquals("Hello World!", "Hello %s".formatted("World!"));
        Assertions.assertEquals("Value: 0,00", "Value: %.2f".formatted(0d));
    }

}
