package pl.mperor.lab.java.data.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class PrimitiveAndReferenceTypesComparisonTest {

    @Test
    public void testComparePrimitiveTypes() {
        Assertions.assertTrue(1 == 1);
        Assertions.assertFalse(1 == 2);
        Assertions.assertTrue(65 == 'A');
    }

    @SuppressWarnings("removal")
    @Test
    public void testCompareReferenceTypes() {
        Assertions.assertTrue(Integer.valueOf(127) == Integer.valueOf(127), "Integer pool (-128, 127)");
        Assertions.assertFalse(Integer.valueOf(128) == Integer.valueOf(128));

        Assertions.assertTrue(new Integer(1).equals(new Integer(1)), "equals to");
        Assertions.assertFalse(new Integer(1) == new Integer(1), "==");
    }

    @Test
    public void testCompareArrays() {
        int[] a1 = {1, 2, 3}, a2 = {1, 2, 3};
        Assertions.assertFalse(a1 == a2);
        Assertions.assertFalse((a1.equals(a2)));
        Assertions.assertTrue(Arrays.equals(a1, a2));
    }
}
