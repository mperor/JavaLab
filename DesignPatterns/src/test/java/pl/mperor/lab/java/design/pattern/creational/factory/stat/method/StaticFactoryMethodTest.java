package pl.mperor.lab.java.design.pattern.creational.factory.stat.method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StaticFactoryMethodTest {

    @Test
    public void testClearNaming() {
        Assertions.assertEquals(new Pair<>("one", "two"), Pair.of("one", "two"));

        Assertions.assertNull(Pair.empty().left());
        Assertions.assertNull(Pair.empty().right());

        var onlyLeft = Pair.left("Only left");
        Assertions.assertNotNull(onlyLeft.left());
        Assertions.assertNull(onlyLeft.right());
    }

    @Test
    public void testTypeConversion() {
        var entry = Map.entry(1, 2);
        var pair = Pair.fromMapEntry(entry);
        Assertions.assertEquals(pair.left(), entry.getKey());
        Assertions.assertEquals(pair.right(), entry.getValue());
    }

    @Test
    public void testOptimizedObjectReuseAkaCaching() {
        Assertions.assertSame(Pair.empty(), Pair.empty());
        Assertions.assertSame(Category.of("Knowledge"), Category.of("Knowledge"));
    }
}