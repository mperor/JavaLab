package pl.mperor.lab.java.quirk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegerCacheTest {

    @Test
    public void testAutoboxingWithinCacheRangeSharesInstance() {
        Integer a = 127, b = 127;
        Assertions.assertTrue(a == b, "autoboxing delegates to Integer.valueOf, which caches -128..127");
    }

    @Test
    public void testAutoboxingOutsideCacheRangeCreatesNewInstance() {
        Integer a = 128, b = 128;
        Assertions.assertFalse(a == b, "128 is outside the cached range, so valueOf allocates a new Integer each time");
    }

    @SuppressWarnings("removal")
    @Test
    public void testExplicitConstructorNeverUsesTheCache() {
        Integer a = new Integer(127), b = new Integer(127);
        Assertions.assertFalse(a == b, "new Integer(int) always allocates, unlike the deprecated-for-removal valueOf cache");
    }

    @Test
    public void testOtherIntegralWrappersCacheMinus128To127() {
        Long l1 = 127L, l2 = 127L;
        Short s1 = 127, s2 = 127;
        Byte b1 = (byte) 127, b2 = (byte) 127;
        Assertions.assertTrue(l1 == l2);
        Assertions.assertTrue(s1 == s2);
        Assertions.assertTrue(b1 == b2, "byte's full range fits in the cache, so every boxed byte is shared");
    }

    @Test
    public void testCharacterCaches0To127() {
        Character c1 = 'A', c2 = 'A';
        Assertions.assertTrue(c1 == c2, "'A' is 65, inside Character's 0..127 cache");

        char outsideCache = 200;
        Character c3 = outsideCache, c4 = outsideCache;
        Assertions.assertFalse(c3 == c4, "200 is outside Character's cache");
    }

    @Test
    public void testFloatingPointWrappersAreNeverCached() {
        Double d1 = 1.0, d2 = 1.0;
        Float f1 = 1.0f, f2 = 1.0f;
        Assertions.assertFalse(d1 == d2, "Double.valueOf never caches, even for small values");
        Assertions.assertFalse(f1 == f2, "Float.valueOf never caches either");
    }

    @Test
    public void testEqualsIsTheCorrectComparisonRegardlessOfCaching() {
        Integer a = 128, b = 128;
        Assertions.assertFalse(a == b);
        Assertions.assertTrue(a.equals(b), "equals compares values, so it is unaffected by whether the cache kicked in");
    }

    @Test
    public void testUnboxingNullThrowsNullPointerException() {
        Integer boxed = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            int unboxed = boxed;
        }, "unboxing null calls Integer.intValue() on a null reference");
    }
}
