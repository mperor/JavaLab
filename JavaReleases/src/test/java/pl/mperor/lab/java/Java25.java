package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.NoSuchElementException;

/// Java 25 (September 2025)
/// [JDK 25](https://openjdk.org/projects/jdk/25)
///
/// - STANDARD FEATURES:
///     - 503:	Remove the 32-bit x86 Port
///     - 506:	Scoped Values
///     - 510:	Key Derivation Function API
///     - 511:	Module Import Declarations
///     - 512:	Compact Source Files and Instance Main Methods
///     - 513:	Flexible Constructor Bodies
///     - 514:	Ahead-of-Time Command-Line Ergonomics
///     - 515:	Ahead-of-Time Method Profiling
///     - 518:	JFR Cooperative Sampling
///     - 519:	Compact Object Headers
///     - 520:	JFR Method Timing & Tracing
///     - 521:	Generational Shenandoah
///
/// - PREVIEW & INCUBATOR:
///     - 470:	PEM Encodings of Cryptographic Objects (Preview)
///     - 502:	Stable Values (Preview)
///     - 505:	Structured Concurrency (Fifth Preview)
///     - 507:  Primitive Types in Patterns, instanceof, and switch (Third Preview)
///     - 508:	Vector API (Tenth Incubator)
///     - 509:	JFR CPU-Time Profiling (Experimental)
public class Java25 {

    @Test
    public void testPrintFromIOClass() {
        var out = TestUtils.setTempSystemOut();
        IO.println("Hello World!"); // ⇒ System.out.println("Hello World!");
        Assertions.assertEquals("Hello World!" + System.lineSeparator(), out.all());
        TestUtils.resetSystemOut();
    }

    @Test
    public void testValidateArgumentBeforeSuperCall() {
        Assertions.assertEquals(5, new PositiveBox(5).value);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PositiveBox(-1));
    }

    static class Box {
        final int value;

        Box(int value) {
            this.value = value;
        }
    }

    /// Before JEP 513, validating/transforming `value` required a private static
    /// helper method invoked inside `super(...)`, since no statement could precede it.
    static class PositiveBox extends Box {
        PositiveBox(int value) {
            if (value <= 0) {
                throw new IllegalArgumentException("value must be positive: " + value);
            }
            super(value);
        }
    }

    @Test
    public void testFieldAssignedBeforeSuperVisibleToOverriddenMethod() {
        var circle = new Circle(2.5);
        Assertions.assertEquals("circle r=2.5", circle.description);
    }

    static class Shape {
        Shape() {
            describe(); // an overridable method invoked from the superclass constructor
        }

        void describe() {
        }
    }

    static class Circle extends Shape {
        final double radius;
        String description;

        Circle(double radius) {
            // Still forbidden in the prologue (would not compile here, before super()):
            //   System.out.println(this);    // cannot reference `this`
            //   describe();                  // cannot call an instance method
            //   System.out.println(radius);  // cannot read an own field back
            this.radius = radius; // JEP 513: assigning an own field is now allowed before super()
            super();
        }

        @Override
        void describe() {
            // radius is already set here, even though super() has not returned yet
            description = "circle r=" + radius;
        }
    }

    static final ScopedValue<String> USER = ScopedValue.newInstance();

    @Test
    public void testScopedValueIsBoundOnlyInsideRunScope() {
        Assertions.assertFalse(USER.isBound());

        ScopedValue.where(USER, "John Doe").run(() -> {
            Assertions.assertTrue(USER.isBound());
            Assertions.assertEquals("John Doe", USER.get());
        });

        // the binding is torn down as soon as run() returns
        Assertions.assertFalse(USER.isBound());
        Assertions.assertThrows(NoSuchElementException.class, USER::get);
    }

    @Test
    public void testNestedRebindingRestoresOuterValueAfterward() {
        ScopedValue.where(USER, "John Doe").run(() -> {
            Assertions.assertEquals("John Doe", USER.get());

            ScopedValue.where(USER, "Jane Doe").run(() -> Assertions.assertEquals("Jane Doe", USER.get()));

            // the nested run() only rebinds USER for its own body; the outer binding is unaffected
            Assertions.assertEquals("John Doe", USER.get());
        });
    }

    @Test
    public void testCallReturnsValueComputedUnderTheBinding() throws Exception {
        String greeting = ScopedValue.where(USER, "John Doe").call(() -> "Hello, " + USER.get());
        Assertions.assertEquals("Hello, John Doe", greeting);
    }

    @Test
    public void testScopedValueHasNoSetUnlikeThreadLocal() {
        var legacy = new ThreadLocal<String>();
        legacy.set("John Doe");
        legacy.set("Jane Doe"); // ThreadLocal is mutable: set() silently replaces the value, anywhere, anytime
        Assertions.assertEquals("Jane Doe", legacy.get());
        legacy.remove(); // must be cleared by hand, or it leaks for the thread's lifetime (e.g. in a pool)

        // ScopedValue has no set()/remove(): the only way to associate a value is where(...).run()/.call(...),
        // and the binding is automatically torn down when that call returns - no leak is possible.
        ScopedValue.where(USER, "John Doe").run(() -> Assertions.assertEquals("John Doe", USER.get()));
        Assertions.assertFalse(USER.isBound());
    }

    @Test
    public void testModuleImportBringsInAllExportedPackagesOfJavaBase() {
        var demo = new JavaBaseImport();
        // List.of(...) (java.util) and new File(...) (java.io) are two different java.base packages,
        // both resolved inside JavaBaseImport.java by its single `import module java.base;` declaration
        Assertions.assertEquals(java.util.List.of("a", "b", "c"), demo.listFromUtilPackage);
        Assertions.assertEquals("file.txt", demo.fileFromIoPackage.getName());
    }

    @Test
    public void testModuleImportAmbiguityResolvedBySingleTypeImport() {
        var demo = new JavaDesktopImportAmbiguity();
        // proof that `names` really is java.util.List, not java.awt.List, see JavaDesktopImportAmbiguity.java
        Assertions.assertEquals(java.util.List.of("John Doe", "Jane Doe"), demo.names);
    }

    /// CompactSourceFile.java has no package declaration, so it lives in the unnamed package and
    /// cannot be imported here (a named package can never import from the unnamed one). It is
    /// launched the way JEP 512 intends: directly by the `java` source launcher, one file, no build step.
    @Test
    public void testCompactSourceFileRunsDirectlyViaSourceLauncher() throws IOException, InterruptedException {
        var javaLauncher = Path.of(System.getProperty("java.home"), "bin", "java");
        var sourceFile = Path.of("src", "main", "java", "pl", "mperor", "lab", "java", "CompactSourceFile.java");

        var process = new ProcessBuilder(javaLauncher.toString(), sourceFile.toString())
                .redirectErrorStream(true)
                .start();
        String output = new String(process.getInputStream().readAllBytes());
        int exitCode = process.waitFor();

        Assertions.assertEquals(0, exitCode, output);
        Assertions.assertEquals(
                "Compact source file & main method here ..." + System.lineSeparator()
                        + "Items: [a, b, c]" + System.lineSeparator(),
                output);
    }

    @Test
    public void testHkdfExtractThenExpandDerivesSecretKeyOfRequestedLength() throws GeneralSecurityException {
        var kdf = KDF.getInstance("HKDF-SHA256");
        byte[] ikm = "shared secret".getBytes(StandardCharsets.UTF_8);
        byte[] salt = "a salt value".getBytes(StandardCharsets.UTF_8);
        byte[] info = "session key".getBytes(StandardCharsets.UTF_8);

        var spec = HKDFParameterSpec.ofExtract().addIKM(ikm).addSalt(salt).thenExpand(info, 32);
        SecretKey key = kdf.deriveKey("AES", spec);

        Assertions.assertEquals("AES", key.getAlgorithm());
        Assertions.assertEquals(32, key.getEncoded().length);
    }

    @Test
    public void testSameInputsDeriveSameKeyDifferentInfoDerivesDifferentKey() throws GeneralSecurityException {
        byte[] ikm = "shared secret".getBytes(StandardCharsets.UTF_8);
        byte[] salt = "a salt value".getBytes(StandardCharsets.UTF_8);

        byte[] keyA = deriveAesKeyBytes(ikm, salt, "session key".getBytes(StandardCharsets.UTF_8));
        byte[] keyB = deriveAesKeyBytes(ikm, salt, "session key".getBytes(StandardCharsets.UTF_8));
        byte[] keyC = deriveAesKeyBytes(ikm, salt, "other purpose".getBytes(StandardCharsets.UTF_8));

        // HKDF is deterministic: identical ikm/salt/info always derive the same key material.
        Assertions.assertArrayEquals(keyA, keyB);
        // Changing only the context-binding `info` derives an unrelated key, even from the same secret.
        Assertions.assertFalse(Arrays.equals(keyA, keyC));
    }

    private static byte[] deriveAesKeyBytes(byte[] ikm, byte[] salt, byte[] info) throws GeneralSecurityException {
        var kdf = KDF.getInstance("HKDF-SHA256");
        var spec = HKDFParameterSpec.ofExtract().addIKM(ikm).addSalt(salt).thenExpand(info, 32);
        return kdf.deriveKey("AES", spec).getEncoded();
    }

    @Test
    public void testDeriveDataReturnsSameRawBytesAsDeriveKey() throws GeneralSecurityException {
        byte[] ikm = "shared secret".getBytes(StandardCharsets.UTF_8);
        byte[] salt = "a salt value".getBytes(StandardCharsets.UTF_8);
        byte[] info = "session key".getBytes(StandardCharsets.UTF_8);
        var spec = HKDFParameterSpec.ofExtract().addIKM(ikm).addSalt(salt).thenExpand(info, 32);

        // deriveKey() and deriveData() run the same derivation; deriveKey() only wraps the
        // resulting bytes into a SecretKey for a named algorithm, deriveData() hands them back raw.
        byte[] rawData = KDF.getInstance("HKDF-SHA256").deriveData(spec);
        byte[] keyBytes = KDF.getInstance("HKDF-SHA256").deriveKey("AES", spec).getEncoded();

        Assertions.assertArrayEquals(keyBytes, rawData);
    }

}
