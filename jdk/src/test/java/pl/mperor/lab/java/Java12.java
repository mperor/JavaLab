package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.AbstractMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/// Java 12™ (March 2019)
/// [JDK 12](https://openjdk.org/projects/jdk/12)
///
/// - STANDARD FEATURES:
///     - 230:	Microbenchmark Suite
///     - 334:	JVM Constants API
///     - 340:	One AArch64 Port, Not Two
///     - 341:	Default CDS Archives (`-Xshare:dump`)
///     - 344:	Abortable Mixed Collections for G1
///     - 346:	Promptly Return Unused Committed Memory from G1 (`-XX:SoftMaxHeapSize`)
///
/// - PREVIEW & INCUBATOR:
///     - 189:	Shenandoah: A Low-Pause-Time Garbage Collector "Experimental" (`-XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC`)
///     - 325:	Switch Expressions (Preview)
public class Java12 {

    @Test
    public void testStringIndentAndTransformMethods() {
        String text = "Hello\nJava 12\n";
        Assertions.assertEquals("  Hello\n  Java 12\n", text.indent(2));
        Assertions.assertEquals(text, text.indent(-4));

        String reversed = "Java 12".transform(StringBuilder::new).reverse().toString();
        Assertions.assertEquals("21 avaJ", reversed);
    }

    @Test
    public void testFilesMismatch() throws Exception {
        Path file1 = Files.createTempFile("file1", ".txt");
        Path file2 = Files.createTempFile("file2", ".txt");

        String helloMessage = "Hello Java 12!";
        Files.writeString(file1, helloMessage);
        Files.writeString(file2, helloMessage);

        Assertions.assertEquals(-1, Files.mismatch(file1, file2));
        Assertions.assertEquals(false, Files.isSameFile(file1, file2));
        Assertions.assertEquals(generateFileHashMD5(file1), generateFileHashMD5(file2));
    }

    private String generateFileHashMD5(Path file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] fileBytes = Files.readAllBytes(file);
        byte[] hashBytes = md.digest(fileBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @Test
    public void testCompactNumberFormat() {
        NumberFormat shortNumberFormat = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        shortNumberFormat.setMaximumFractionDigits(2);
        Assertions.assertEquals("1.2K", shortNumberFormat.format(1_200));
        Assertions.assertEquals("1.02M", shortNumberFormat.format(1_020_000));
    }

    @Test
    public void testTeeingCollector() {
        List<String> words = List.of("one", "two", "three");

        AbstractMap.SimpleEntry<Integer, Long> pair = words.stream().collect(Collectors.teeing(
                Collectors.summingInt(String::length),
                Collectors.counting(),
                AbstractMap.SimpleEntry::new)
        );

        Assertions.assertEquals(11, pair.getKey());
        Assertions.assertEquals(3, pair.getValue());
    }

    @Test
    public void testCompletableExceptionallyCompose() {
        var errorThrowingPlanA = CompletableFuture.supplyAsync(() -> 1 / 0);
        var noErrorPlanB = CompletableFuture.supplyAsync(() -> 0);

        errorThrowingPlanA.exceptionallyComposeAsync(ex -> {
            Assertions.assertInstanceOf(ArithmeticException.class, ex);
            return noErrorPlanB;
        }).thenAccept(result -> Assertions.assertEquals(0, result));
    }

}
