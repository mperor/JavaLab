package pl.mperor.lab.java.quirk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BigDecimalEqualsVsCompareToTest {

    @Test
    public void testEqualsComparesScaleButCompareToIgnoresIt() {
        BigDecimal two = new BigDecimal("2.0");
        BigDecimal twoWithMoreZeros = new BigDecimal("2.00");

        Assertions.assertFalse(two.equals(twoWithMoreZeros), "equals also compares scale, and 2.0 has scale 1 while 2.00 has scale 2");
        Assertions.assertEquals(0, two.compareTo(twoWithMoreZeros), "compareTo only compares numeric value, so it treats them as equal");
    }

    @Test
    public void testHashSetKeepsBothButTreeSetKeepsOne() {
        BigDecimal two = new BigDecimal("2.0");
        BigDecimal twoWithMoreZeros = new BigDecimal("2.00");

        Set<BigDecimal> hashSet = new HashSet<>();
        hashSet.add(two);
        hashSet.add(twoWithMoreZeros);
        Assertions.assertEquals(2, hashSet.size(), "HashSet uses equals/hashCode, which treat differently-scaled values as distinct");

        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(two);
        treeSet.add(twoWithMoreZeros);
        Assertions.assertEquals(1, treeSet.size(), "TreeSet uses compareTo for uniqueness, so the second insert is considered a duplicate");
    }

    @Test
    public void testDoubleConstructorVsValueOf() {
        BigDecimal fromDoubleConstructor = new BigDecimal(0.1);
        BigDecimal fromValueOf = BigDecimal.valueOf(0.1);

        Assertions.assertNotEquals(fromDoubleConstructor, fromValueOf, "new BigDecimal(double) exposes the exact binary representation of 0.1, which is not 0.1");
        Assertions.assertEquals("0.1", fromValueOf.toString(), "BigDecimal.valueOf(double) goes through Double.toString, giving the shortest decimal that round-trips");
        Assertions.assertTrue(fromDoubleConstructor.toPlainString().length() > fromValueOf.toPlainString().length(),
                "the double constructor's result carries dozens of extra digits from the binary approximation");
    }

    @Test
    public void testStripTrailingZerosNormalizesScaleForEquals() {
        BigDecimal two = new BigDecimal("2.0");
        BigDecimal twoWithMoreZeros = new BigDecimal("2.00");

        Assertions.assertEquals(two.stripTrailingZeros(), twoWithMoreZeros.stripTrailingZeros(),
                "stripTrailingZeros removes the scale difference, so equals now agrees with compareTo");

        BigDecimal strippedZero = new BigDecimal("0.00").stripTrailingZeros();
        Assertions.assertEquals(0, strippedZero.compareTo(BigDecimal.ZERO), "an exact zero still compares equal to ZERO after stripping zeros");
    }
}
