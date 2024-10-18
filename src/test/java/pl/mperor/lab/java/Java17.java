package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.HexFormat;
import java.util.ServiceLoader;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * Java 17 (September 2021)
 */
public class Java17 {

    @Test
    public void testStronglyEncapsulatedInternals() {
        Assertions.assertThrows(InaccessibleObjectException.class, () -> deepLookIntoStringBytes("Hello"));
    }

    private static byte[] deepLookIntoStringBytes(String string) throws IllegalAccessException, NoSuchFieldException {
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);
        return (byte[]) valueField.get(string);
    }

    @Test
    public void testSwitchPatternMatching() {
        Assertions.assertEquals("String: Hello", switchOverClasses("Hello"));
        Assertions.assertEquals("int: 1", switchOverClasses(1));
        Assertions.assertEquals("long: 13", switchOverClasses(13L));
        Assertions.assertEquals("boolean: true", switchOverClasses(true));
        Assertions.assertEquals("null", switchOverClasses(null));
        record Person(String name, String surname) {
            @Override
            public String toString() {
                return "%s %s".formatted(name, surname);
            }
        }
        Assertions.assertEquals("Object: John Doe", switchOverClasses(new Person("John", "Doe")));
    }

    private static String switchOverClasses(Object obj) {
        return switch (obj) {
            case String s -> String.format("String: %s", s);
            case Integer i -> String.format("int: %d", i);
            case Long l -> String.format("long: %d", l);
            case Boolean b -> String.format("boolean: %s", b);
            case null -> "null";
            default -> "Object: " + obj;
        };
    }

    @Test
    public void testRandomGeneratorFactory() {
        var generators = ServiceLoader.load(RandomGenerator.class).stream()
                .map(provider -> provider.type().getCanonicalName())
                .peek(System.out::println)
                .toList();

        Assertions.assertTrue(generators.contains("java.util.Random"));
        var defaultRandomGenerator = RandomGeneratorFactory.getDefault().create();
        Assertions.assertEquals("jdk.random.L32X64MixRandom", defaultRandomGenerator.getClass().getCanonicalName());
    }

    @Test
    void testHexFormat() {
        HexFormat hexFormat = HexFormat.of();
        String hex = hexFormat.formatHex(new byte[]{0x1A, 0x2B, 0x3C});
        Assertions.assertEquals("1a2b3c", hex);
        Assertions.assertArrayEquals(new byte[]{0x1A, 0x2B, 0x3C}, hexFormat.parseHex(hex));
        Assertions.assertFalse(HexFormat.isHexDigit('g'));
        Assertions.assertEquals(16 + 10, HexFormat.fromHexDigits("1a"));
    }

}