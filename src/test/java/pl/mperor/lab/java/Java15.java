package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Locale;

/// Java 15™ (September 2020)
/// [JDK 15](https://openjdk.org/projects/jdk/15)
///
/// - STANDARD FEATURES:
///     - 378:	Text Blocks
///     - 339:	Edwards-Curve Digital Signature Algorithm (EdDSA)
///     - 371:	Hidden Classes
///     - 372:	Remove the Nashorn JavaScript Engine
///     - 373:	Reimplement the Legacy DatagramSocket API
///     - 374:	Disable and Deprecate Biased Locking (`-XX:+UseBiasedLocking`)
///     - 377:	ZGC: A Scalable Low-Latency Garbage Collector (`-XX:+UseZGC`)
///     - 379:	Shenandoah: A Low-Pause-Time Garbage Collector (`-XX:+UseShenandoahGC`)
///     - 381:	Remove the Solaris and SPARC Ports
///     - 385:	Deprecate RMI Activation for Removal [Java17#testRmiActivationRemoved()]
///
/// - PREVIEW & INCUBATOR:
///     - 360:	Sealed Classes (Preview)
///     - 375:	Pattern Matching for instanceof (Second Preview)
///     - 384:	Records (Second Preview)
///     - 383:	Foreign-Memory Access API (Second Incubator)
public class Java15 {

    @Test
    public void testTextBlock() {
        String json = """
                {
                    "login": "admin",
                    "password": "*****"
                }
                """;
        Assertions.assertTrue(json.contains("\"login\": \"admin\""));

        String skippedLines = """
                a\
                b\
                c\
                """;
        Assertions.assertEquals("abc", skippedLines);

        String notSkippedSpaces = """
                a \s
                b\s
                c""";
        Assertions.assertEquals("a  \nb \nc", notSkippedSpaces);
    }

    @Test
    public void testStringFormatted() {
        Assertions.assertEquals("Hello World!", "Hello %s".formatted("World!"));
        Assertions.assertEquals("Value: 0.00", String.format(Locale.US, "Value: %.2f", 0d));
    }

    @Test
    public void testEdwardsCurveDigitalSignatureAlgorithm() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String edwardsAlgorithm = "Ed25519";
        KeyPairGenerator generator = KeyPairGenerator.getInstance(edwardsAlgorithm);
        KeyPair keyPair = generator.generateKeyPair();
        byte[] bytes = "secret".getBytes();

        Signature sig = Signature.getInstance(edwardsAlgorithm);
        sig.initSign(keyPair.getPrivate());
        sig.update(bytes);
        byte[] signed = sig.sign();

        Signature verificationSignature = Signature.getInstance(edwardsAlgorithm);
        verificationSignature.initVerify(keyPair.getPublic());
        verificationSignature.update(bytes);
        Assertions.assertTrue(verificationSignature.verify(signed));
    }

    @Test
    public void testHiddenClass() throws Throwable {
        Path path = Paths.get("build/classes/java/main/pl/mperor/lab/java/Hidden.class");

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Class<?> hiddenClass = lookup.defineHiddenClass(Files.readAllBytes(path), true, MethodHandles.Lookup.ClassOption.NESTMATE)
                .lookupClass();
        MethodHandle methodHandle = lookup.findStatic(hiddenClass, "lookup", MethodType.methodType(int.class));
        Assertions.assertEquals(1, methodHandle.invoke());
    }

    @Test
    public void testNashornEngineRemoved() {
        Assertions.assertThrows(ClassNotFoundException.class, () -> {
            Class.forName("jdk.nashorn.api.scripting.NashornScriptEngineFactory");
        });
    }

    @Test
    public void testDatagramSocket() throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket();
        Assertions.assertNotNull(datagramSocket);
    }

}