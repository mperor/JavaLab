package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleSupportTest {

    @Test
    public void testSimulatedSystemInScanner() {
        byte[] bytes = """
                Hello
                World""".getBytes(StandardCharsets.UTF_8);

        var original = System.in;
        System.setIn(new ByteArrayInputStream(bytes));

        try (Scanner sc = new Scanner(System.in)) {
            Assertions.assertEquals("Hello", sc.next());
            Assertions.assertEquals("World", sc.next());
        }
        System.setIn(original);
    }

}
