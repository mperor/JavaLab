package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import pl.mperor.lab.TestUtils;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/// Java 16™ (March 2021)
/// [JDK 16](https://openjdk.org/projects/jdk/16)
///
/// - STANDARD FEATURES:
///     - 395:	Records
///     - 394:	Pattern Matching for instanceof
///     - 390:	Warnings for Value-Based Classes
///     - 392:	Packaging Tool
///     - 396:	Strongly Encapsulate JDK Internals by Default
///     - 376:	ZGC: Concurrent Thread-Stack Processing
///     - 380:	Unix-Domain Socket Channels
///     - 387:	Elastic Metaspace
///     - 347:	Enable C++14 Language Features
///     - 386:	Alpine Linux Port
///     - 388:	Windows/AArch64 Port
///     - 357:	Migrate from Mercurial to Git
///     - 369:	Migrate to GitHub
///
/// - PREVIEW & INCUBATOR:
///     - 389:	Foreign Linker API (Incubator)
///     - 397:	Sealed Classes (Second Preview)
///     - 393:	Foreign-Memory Access API (Third Incubator)
///     - 338:	Vector API (Incubator)
public class Java16 {

    // POJOs
    @Test
    public void testPairRecord() {
        var pair = Pair.of("Boy", "Girl");
        Assertions.assertEquals("Boy", pair.left);
        Assertions.assertEquals("Girl", pair.right);
        Assertions.assertEquals("Pair[left=Boy, right=Girl]", pair.toString());
        Assertions.assertEquals(Pair.of(1, 1), Pair.of(1, 1));
        Assertions.assertNotEquals(Pair.of(0, 0), Pair.of(1, 1));
        Assertions.assertThrows(NullPointerException.class, () -> Pair.of(0, null));
    }

    record Pair<L, R>(L left, R right) {
        Pair {
            Objects.requireNonNull(left);
            Objects.requireNonNull(right);
        }

        public static <L, R> Pair<L, R> of(L left, R right) {
            return new Pair<>(left, right);
        }
    }

    @Test
    public void testPatternMatchingInstanceOfAkaSmartCasting() {
        Object o = "Hello String!";
        if (o instanceof String s) {
            Assertions.assertNotNull(s);
            Assertions.assertInstanceOf(String.class, s);
        }
        Assertions.assertTrue(nonEmptyString("Hello World!"));
        Assertions.assertFalse(nonEmptyString(null));
    }

    private static boolean nonEmptyString(Object obj) {
        return (obj instanceof String str) && !str.isEmpty();
    }

    @SuppressWarnings({"removal", "synchronization"})
    @Test
    public void testValueBasedClasses() {
        // warning: [removal] Long(long) in Long has been deprecated and marked for removal
        Long longByConstructor = new Long(13);

        // warning: [synchronization] attempt to synchronize on an instance of a value-based class
        synchronized (longByConstructor) {
            System.out.println("From the synchronized block!");
        }

        Long longByFactoryMethod = Long.valueOf(13L);
        Assertions.assertFalse(longByConstructor == longByFactoryMethod);
        Assertions.assertTrue(longByConstructor.equals(longByFactoryMethod));
        Assertions.assertTrue(Long.valueOf(13L) == longByFactoryMethod);
    }

    @Test
    public void testPeriodSupport() {
        LocalTime date = LocalTime.parse("16:00:00");
        // symbol "B" alternative to the am/pm format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h B", Locale.US);
        Assertions.assertEquals("4 in the afternoon", date.format(formatter));
    }

    @Test
    public void testStreamToList() {
        var source = List.of(1, 2, 3);
        var toList = source.stream().toList();
        var collectToList = source.stream().collect(Collectors.toList());
        Assertions.assertEquals(toList, collectToList);
    }

    @Test
    public void testLocalElements() {
        var out = TestUtils.setTempSystemOut();

        interface LocalInterface {
            default void call() {
                System.out.println("Local interface work!");
            }
        }

        record LocalRecord() implements LocalInterface {
            @Override
            public void call() {
                System.out.println("Local records work!");
            }
        }

        enum LocalEnum implements LocalInterface {
            SINGLE;

            @Override
            public void call() {
                System.out.println("Local enums work!");
            }
        }

        new LocalRecord().call();
        LocalEnum.SINGLE.call();
        new LocalInterface() {}.call();

        var lines = out.lines();
        Assertions.assertEquals("Local records work!", lines.getFirst());
        Assertions.assertEquals("Local enums work!", lines.getSecond());
        Assertions.assertEquals("Local interface work!", lines.getThird());
        TestUtils.resetSystemOut();
    }

    @Disabled("Dependent on the OS and additional libraries, besides having a long execution time.")
    @Test
    public void testJPackage() throws IOException, InterruptedException {
        Process process = new ProcessBuilder(
                "jpackage",
                "--name", "j16pack",
                "--input", "./build/libs",
                "--main-jar", "JavaLab-1.0-SNAPSHOT.jar",
                "--main-class", "pl.mperor.lab.java.Main"
        ).start();

        Assertions.assertEquals(0, process.waitFor());
        process.destroy();
    }

    @EnabledOnOs(OS.LINUX)
    @Test
    public void testUnixDomainSocketChannels() throws IOException {
        Path socketPath = Paths.get("/tmp/unix_socket");
        Files.deleteIfExists(socketPath);
        UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(socketPath);

        Assertions.assertNotNull(socketAddress);
        Assertions.assertNotNull(StandardProtocolFamily.UNIX);
        try (var server = ServerSocketChannel.open(StandardProtocolFamily.UNIX).bind(socketAddress)) {
            Assertions.assertNotNull(server);
        }
    }

}