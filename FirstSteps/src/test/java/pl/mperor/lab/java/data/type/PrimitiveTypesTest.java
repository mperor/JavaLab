package pl.mperor.lab.java.data.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrimitiveTypesTest {

    @Test
    public void testByteAkaSmallInteger() {
        byte byteInBinary = 0b0; // same as 0
        Assertions.assertInstanceOf(Byte.class, byteInBinary, "Wrapper type");
        Assertions.assertEquals(127, Byte.MAX_VALUE, "Max value");
        Assertions.assertEquals(-128, Byte.MIN_VALUE, "Min value");
        Assertions.assertEquals(8, Byte.SIZE, "Number of bits");
        Assertions.assertEquals(1, Byte.BYTES, "Number of bytes");
    }

    @Test
    public void testShortAkaShortInteger() {
        short shortInHex = 0x0; // same as 0
        Assertions.assertInstanceOf(Short.class, shortInHex, "Wrapper type");
        Assertions.assertEquals(32_767, Short.MAX_VALUE, "Max value");
        Assertions.assertEquals(-32_768, Short.MIN_VALUE, "Min value");
        Assertions.assertEquals(16, Short.SIZE, "Number of bits");
        Assertions.assertEquals(2, Short.BYTES, "Number of bytes");
    }

    @Test
    public void testInteger() {
        int integer = 0;
        Assertions.assertInstanceOf(Integer.class, integer, "Wrapper type");
        Assertions.assertEquals(2_147_483_647, Integer.MAX_VALUE, "Max value");
        Assertions.assertEquals(-2_147_483_648, Integer.MIN_VALUE, "Min value");
        Assertions.assertEquals(32, Integer.SIZE, "Number of bits");
        Assertions.assertEquals(4, Integer.BYTES, "Number of bytes");
    }

    @Test
    public void testLongAkaLargeInteger() {
        long largeInteger = 0L;
        Assertions.assertInstanceOf(Long.class, largeInteger, "Wrapper type");
        Assertions.assertEquals(9_223_372_036_854_775_807L, Long.MAX_VALUE, "Max value");
        Assertions.assertEquals(-9_223_372_036_854_775_808L, Long.MIN_VALUE, "Min value");
        Assertions.assertEquals(64, Long.SIZE, "Number of bits");
        Assertions.assertEquals(8, Long.BYTES, "Number of bytes");
    }

    /// [IEEE 754 Standard for Floating-Point Arithmetic](https://standards.ieee.org/ieee/754/6210)
    @Test
    public void testFloatAkaSinglePrecisionFloatingPoint() {
        float singlePrecisionFloatingPoint = 0f;
        Assertions.assertInstanceOf(Float.class, singlePrecisionFloatingPoint, "Wrapper type");
        Assertions.assertEquals(3.4028235e+38f, Float.MAX_VALUE, "Max value (7 decimal digits)");
        Assertions.assertEquals(1.4e-45f, Float.MIN_VALUE, "Min value (7 decimal digits)");
        Assertions.assertEquals(32, Float.SIZE, "Number of bits");
        Assertions.assertEquals(4, Float.BYTES, "Number of bytes");
    }

    @Test
    public void testDoubleAkaDoublePrecisionFloatingPoint() {
        double doublePrecisionFloatingPoint = 0d;
        Assertions.assertInstanceOf(Double.class, doublePrecisionFloatingPoint, "Wrapper type");
        Assertions.assertEquals(1.7976931348623157e+308, Double.MAX_VALUE, "Max value (15 decimal digits)");
        Assertions.assertEquals(4.9e-324, Double.MIN_VALUE, "Min value (15 decimal digits)");
        Assertions.assertEquals(64, Double.SIZE, "Number of bits");
        Assertions.assertEquals(8, Double.BYTES, "Number of bytes");
    }

    @Test
    public void testCharacter() {
        char character = 'a';
        Assertions.assertInstanceOf(Character.class, character, "Wrapper type");
        Assertions.assertEquals('\uFFFF', Character.MAX_VALUE, "Max value");
        Assertions.assertEquals('\u0000', Character.MIN_VALUE, "Min value");
        Assertions.assertEquals(16, Character.SIZE, "Number of bits");
        Assertions.assertEquals(2, Character.BYTES, "Number of bytes");
    }

    @Test
    public void testBoolean() {
        boolean bool = false;
        Assertions.assertInstanceOf(Boolean.class, bool, "Wrapper type");
        Assertions.assertEquals(true, Boolean.TRUE);
        Assertions.assertEquals(false, Boolean.FALSE);
    }

}
