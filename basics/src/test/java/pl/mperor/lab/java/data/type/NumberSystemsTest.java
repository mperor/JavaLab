package pl.mperor.lab.java.data.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class NumberSystemsTest {

    @Test
    public void testBinary() {
        String raw = "1011";
        int radix = 2;
        Assertions.assertTrue(Pattern.compile("[01]+").asMatchPredicate().test(raw));
        Assertions.assertEquals(0b1011, Integer.parseInt(raw, radix));
    }

    @Test
    public void testOctal() {
        String raw = "13";
        int radix = 8;
        Assertions.assertTrue(Pattern.compile("[0-7]+").asMatchPredicate().test(raw));
        Assertions.assertEquals(013, Integer.parseInt(raw, radix));
    }

    @Test
    public void testHex() {
        String raw = "1a";
        int radix = 16;
        Assertions.assertTrue(Pattern.compile("[0-9A-Fa-f]+").asMatchPredicate().test(raw));
        Assertions.assertEquals(0x1a, Integer.parseInt(raw, radix));
    }

    @Test
    public void testDecimal() {
        String raw = "111";
        int radix = 10;
        Assertions.assertTrue(Pattern.compile("[0-9]+").asMatchPredicate().test(raw));
        Assertions.assertEquals(111, Integer.parseInt(raw, radix));
    }

}
