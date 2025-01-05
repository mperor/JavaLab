package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.lang.JavaBean;
import pl.mperor.lab.java.sealed.*;

import java.applet.Applet;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.HexFormat;
import java.util.Set;
import java.util.random.RandomGeneratorFactory;

/// Java 17™ (September 2021)
/// [JDK 17](https://openjdk.org/projects/jdk/17)
///
/// - STANDARD FEATURES:
///     - 409:	Sealed Classes
///     - 403:	Strongly Encapsulate JDK Internals
///     - 356:	Enhanced Pseudo-Random Number Generators
///     - 306:	Restore Always-Strict Floating-Point Semantics
///     - 398:	Deprecate the Applet API for Removal
///     - 411:	Deprecate the Security Manager for Removal
///     - 407:	Remove RMI Activation
///     - 410:	Remove the Experimental AOT and JIT Compiler
///     - 415:	Context-Specific Deserialization Filters
///     - 382:	New macOS Rendering Pipeline
///     - 391:	macOS/AArch64 Port
///
/// - PREVIEW & INCUBATOR:
///     - 406:	Pattern Matching for switch (Preview)
///     - 412:	Foreign Function & Memory API (Incubator)
///     - 414:	Vector API (Second Incubator)
public class Java17 {

    @Test
    public void testSealedClassesAndInterfaces() {
        Assertions.assertTrue(Sealed.class.isSealed());
        Assertions.assertFalse(NonSealed.class.isSealed());
        Assertions.assertFalse(Final.class.isSealed());
        Assertions.assertTrue(AlsoSealed.class.isSealed());
        Assertions.assertFalse(AlsoFinal.class.isSealed());
        Assertions.assertFalse(ImplicitlyFinal.class.isSealed());

        Assertions.assertEquals("final", switchSealed(new Final()));
        Assertions.assertEquals("non-sealed", switchSealed(new NonSealed()));
        Assertions.assertEquals("sealed", switchSealed(new AlsoSealed()));
        Assertions.assertEquals("implicitly final", switchSealed(new ImplicitlyFinal()));

        SealedClient client = new SealedClient(new NonSealedChild());
        Assertions.assertEquals("non-sealed", client.call());
    }

    private static String switchSealed(Sealed sealed) {
        return switch (sealed) {
            case AlsoSealed a -> a.alsoSealedMethod();
            case Final f -> f.finalMethod();
            case NonSealed ns -> ns.nonSealedMethod();
            case ImplicitlyFinal r -> r.implicitlyFinalMethod();
        };
    }

    private static class NonSealedChild extends NonSealed {}

    private static class NotPermittedSealedChild /* implements Sealed */ {}

    private static class SealedClient {
        private Sealed s;

        public SealedClient(Sealed s) {
            this.s = s;
        }

        public String call() {
            return switchSealed(s);
        }
    }

    /**
     * You may need this JVM argument:
     * --add-opens java.base/java.lang=ALL-UNNAMED
     */
    @Test
    public void testStronglyEncapsulatedInternals() {
        Assertions.assertThrows(InaccessibleObjectException.class, () -> deepLookIntoStringBytes("Hello"));
        new ProcessBuilder("java", "--add-opens", "java.base/java.lang=ALL-UNNAMED");
    }

    private static byte[] deepLookIntoStringBytes(String string) throws IllegalAccessException, NoSuchFieldException {
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);
        return (byte[]) valueField.get(string);
    }

    @Test
    public void testRandomGeneratorFactory() {
        var defaultRandomGenerator = RandomGeneratorFactory.getDefault().create();
        Assertions.assertEquals("jdk.internal.random.L32X64MixRandom", defaultRandomGenerator.getClass().getCanonicalName());

        var generators = RandomGeneratorFactory.all()
                .map(fac -> fac.create().getClass().getCanonicalName())
                .peek(System.out::println)
                .toList();
        Assertions.assertTrue(generators.contains("java.util.Random"));
    }

    @Test
    public void testHexFormat() {
        HexFormat hexFormat = HexFormat.of();
        String hex = hexFormat.formatHex(new byte[]{0x1A, 0x2B, 0x3C});
        Assertions.assertEquals("1a2b3c", hex);
        Assertions.assertArrayEquals(new byte[]{0x1A, 0x2B, 0x3C}, hexFormat.parseHex(hex));
        Assertions.assertFalse(HexFormat.isHexDigit('g'));
        Assertions.assertEquals(16 + 10, HexFormat.fromHexDigits("1a"));
    }

    @Test
    public void testAlwaysStrictFloatingPointSemantics() {
        Assertions.assertEquals(4, strictPower(2, 2));
    }

    // warning: [strictfp] as of release 17, all floating-point expressions are evaluated strictly and 'strictfp' is not required
    @SuppressWarnings("strictfp")
    public static strictfp double strictPower(double a, double b) {
        return Math.pow(a, b);
    }

    @SuppressWarnings("removal")
    @Test
    public void testDeprecated() {
        // warning: [removal] Applet in java.applet has been deprecated and marked for removal
        Applet appletDeprecatedForRemoval;
        // warning: [removal] SecurityManager in java.lang has been deprecated and marked for removal
        SecurityManager securityManager = System.getSecurityManager();
    }

    @Test
    public void testRmiActivationRemoved() {
        Assertions.assertFalse(ModuleLayer.boot().findModule("java.rmi").stream()
                .map(Module::getPackages)
                .flatMap(Set::stream)
                .anyMatch("java.rmi.activation"::equals));
    }

    @Test
    public void testDeserializationFilters() throws IOException, ClassNotFoundException {
        var file = new File("src/test/resources/bean");

        var basePackageFilter = ObjectInputFilter.Config.createFilter("java.base/*;!*");
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            in.setObjectInputFilter(basePackageFilter);
            Assertions.assertThrows(InvalidClassException.class, () -> in.readObject());
        }

        ObjectInputFilter onlyJavaBeansFilter = ObjectInputFilter.allowFilter(JavaBean.class::equals, ObjectInputFilter.Status.REJECTED);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            in.setObjectInputFilter(onlyJavaBeansFilter);
            Assertions.assertNotNull(in.readObject());
        }
    }

}