package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Java 12 (March 2019)
 */
public class Java12 {

    @Test
    public void testSwitchExpression() {
        Assertions.assertEquals("Working Day", getTypeOfDayByNumber(1));
        Assertions.assertEquals("Day Off", getTypeOfDayByNumber(6));
        Assertions.assertThrows(IllegalStateException.class, () -> getTypeOfDayByNumber(0));
    }

    private String getTypeOfDayByNumber(int dayOfWeek) {
        return switch (dayOfWeek) {
            case 1, 2, 3, 4, 5 -> "Working Day";
            case 6, 7 -> {
                // code block example
                yield "Day Off";
            }
            default -> throw new IllegalStateException("Unexpected value: " + dayOfWeek);
        };
    }

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

    private String generateFileHashMD5(Path file) throws IOException, NoSuchAlgorithmException, NoSuchAlgorithmException, IOException {
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

}
