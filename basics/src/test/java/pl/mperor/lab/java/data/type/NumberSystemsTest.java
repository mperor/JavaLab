package pl.mperor.lab.java.data.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberSystemsTest {

    @Test
    public void testBinary() {
        String raw = "1011";
        int radix = 2;
        Assertions.assertTrue(raw.matches("[01]+"));
        Assertions.assertEquals(0b1011, Integer.parseInt(raw, radix));
    }

    @Test
    public void testOctal() {
        String raw = "13";
        int radix = 8;
        Assertions.assertTrue(raw.matches("[0-7]+"));
        Assertions.assertEquals(013, Integer.parseInt(raw, radix));
    }

    @Test
    public void testHex() {
        String raw = "1a";
        int radix = 16;
        Assertions.assertTrue(raw.matches("[0-9A-Fa-f]+"));
        Assertions.assertEquals(0x1a, Integer.parseInt(raw, radix));
    }

    @Test
    public void testDecimal() {
        String raw = "111";
        int radix = 10;
        Assertions.assertTrue(raw.matches("[0-9]+"));
        Assertions.assertEquals(111, Integer.parseInt(raw, radix));
    }

}
