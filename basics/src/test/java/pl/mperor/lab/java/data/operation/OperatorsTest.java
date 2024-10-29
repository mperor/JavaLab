package pl.mperor.lab.java.data.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperatorsTest {

    @Test
    public void testUnaryArithmeticOperators() {
        Assertions.assertEquals(1, +1, "plus");
        Assertions.assertEquals(-1, -1, "minus");
        int i = 10;
        Assertions.assertEquals(10, i++, "post-increment");
        Assertions.assertEquals(10, --i, "pre-decrement");
    }

    @Test
    public void testBinaryArithmeticOperators() {
        Assertions.assertEquals(3, 1 + 2, "addition");
        Assertions.assertEquals(1, 2 - 1, "subtraction");
        Assertions.assertEquals(4, 2 * 2, "multiplication");
        Assertions.assertEquals(2, 4 / 2, "division");
        Assertions.assertEquals(2, 5 % 3, "modulo");
        Assertions.assertEquals("ab", "a" + "b", "string concatenation");
    }

    @Test
    public void testAssignmentOperators() {
        int i = 1;
        i *= 2; // same as i = i * 2;
        String s = "a";
        s += "b";
        Assertions.assertEquals(2, i);
        Assertions.assertEquals("ab", s);
    }

    @Test
    public void testComparisonOperators() {
        Assertions.assertTrue(1 == 1, "equals to");
        Assertions.assertTrue(1 != 2, "not equals to");
        Assertions.assertTrue(2 > 1, "greater than");
        Assertions.assertTrue(1 < 2, "less than");
        Assertions.assertFalse(2 >= 4, "greater than or equals to");
        Assertions.assertFalse(4 <= 2, "less than or equals to");
    }

    @Test
    public void testLogicalOperators() {
        Assertions.assertTrue(true & true, "and");
        Assertions.assertFalse(false && true, "short and");
        Assertions.assertTrue(true | true, "or");
        Assertions.assertTrue(false || true, "short or");
        Assertions.assertFalse(!true, "not");
        Assertions.assertFalse(true ^ true, "xor");
    }

    @Test
    public void testBitwiseOperators() {
        Assertions.assertEquals(0b0001, 0b1001 & 0b0011, "and");
        Assertions.assertEquals(0b1011, 0b1001 | 0b0011, "or");
        Assertions.assertEquals(0b1010, 0b1001 ^ 0b0011, "xor");
        Assertions.assertEquals(0b11111111_11111111_11111111_11110110, ~0b1001, "flip");
        Assertions.assertEquals(0b0110, 0b0011 << 1, "left shift");
        Assertions.assertEquals(0b0001, 0b0011 >> 1, "right shift");
        Assertions.assertEquals(
                0b01000000_00000000_00000000_00000000,
                0b10000000_00000000_00000000_00000001 >>> 1,
                "unsigned right shift"
        );
    }

    @Test
    public void testTernaryOperator() {
        Assertions.assertTrue(isEmpty(null));
        Assertions.assertTrue(isEmpty(""));
        Assertions.assertFalse(isEmpty("not empty"));
    }

    private static boolean isEmpty(String string) {
        return string == null ? true : string.isEmpty();
    }

}
