package pl.mperor.lab.java.data.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReferenceTypesTest {

    @Test
    public void testReferenceTypes() {
        Assertions.assertTrue(int[].class.isArray());
        Assertions.assertTrue(isClass(Class.class));
        Assertions.assertTrue(Interface.class.isInterface());
        Assertions.assertTrue(Record.class.isRecord());
        Assertions.assertTrue(Enumeration.class.isEnum());
        Assertions.assertTrue(Annotation.class.isAnnotation());
    }

    private static boolean isClass(java.lang.Class<?> clazz) {
        return !clazz.isInterface() && !clazz.isEnum() && !clazz.isAnnotation() && !clazz.isPrimitive();
    }

    public class Class {}

    public interface Interface {}

    public record Record() {}

    public enum Enumeration {INSTANCE}

    public @interface Annotation {}

}
