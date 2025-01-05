package pl.mperor.lab.java.data.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimitiveTypesConversionTest {

    @Test
    public void testWidening() {
        byte b = 127;
        short s = b;
        int i = s;
        long l = i;
        Assertions.assertEquals(127, s);
        Assertions.assertEquals(127, i);
        Assertions.assertEquals(127, l);

        float f = 0.1f;
        double d = f;
        Assertions.assertTrue(f == d);

        char c = 'A';
        l = i = c;
        Assertions.assertTrue(i == 65 && l == 65);
    }

    @Test
    public void testCutting() {
        float f = 1.123f;
        int i = (int) f;
        Assertions.assertEquals(1, i);

        double d = 2.123;
        long l = (long) d;
        Assertions.assertEquals(2, l);

        long number = 1_000_000_000_000_000_001L;
        double converted = number;
        Assertions.assertNotEquals(0, number - (long) converted, "Floating point numbers and integers are stored in different way in Java memory");
    }

    @Test
    public void testNarrowingNotOverflow() {
        long l = 127;
        int i = (int) l;
        short s = (short) l;
        byte b = (byte) l;
        Assertions.assertEquals(127, i);
        Assertions.assertEquals(127, s);
        Assertions.assertEquals(127, b);
    }

    @Test
    public void testNarrowingOverflow() {
        long l = 4_294_967_297L;
        int i = (int) l;
        short s = (short) l;
        byte b = (byte) l;
        Assertions.assertEquals(1, i);
        Assertions.assertEquals(1, s);
        Assertions.assertEquals(1, b);
    }

    @Test
    public void testUnboxingAndAutoboxing() {
        Integer bigInt = 1;
        int smallInt = bigInt;
        Assertions.assertEquals(bigInt, smallInt);
    }
}
