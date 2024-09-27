package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java 1.4 (February 2002)
 */
public class Java4 {

    @Test
    public void testAssertionKeyWord() {
        Object object = null;
        AssertionError assertionError = Assertions.assertThrows(AssertionError.class, () -> {
            assert object != null : "Should be not null!";
        });
        Assertions.assertEquals("Should be not null!", assertionError.getMessage());
    }

    @Test
    public void testNewInputOutputAkaNIO() throws IOException {
        Path path = Paths.get("src", "test", "resources", "nio.txt");
        byte[] fileBytes = Files.readAllBytes(path);
        String content = new String(fileBytes);
        Assertions.assertEquals("Hello NIO!", content);
    }

    @Test
    public void testRegex() {
        Pattern pattern = Pattern.compile("<title>(.*)</title>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(getTestHtml());
        Assertions.assertTrue(matcher.find());
        Assertions.assertEquals("Title", matcher.group(1));
    }

    private String getTestHtml() {
        return """
                \
                <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Title</title>
                    </head>
                    <body>
                        <h1>Hello World!</h1>
                    </body>
                </html>
                """;
    }

    @Test
    public void testExceptionChaining() {
        Exception excpectedException = Assertions.assertThrows(Exception.class, () -> {
            try {
                throw new IOException("IO Exception");
            } catch (Exception e) {
                throw new Exception("New Exception", e);
            }
        });
        Assertions.assertEquals(excpectedException.getCause().getMessage(), "IO Exception");
    }

    @Test
    public void testLoggingAPI() {
        Logger logger = Logger.getLogger(Java4.class.getName());
        logger.info("This is an informational message.");
        logger.warning("This is a warning message.");
        Assertions.assertEquals("pl.mperor.lab.java.Java4", logger.getName());
    }

    @Test
    public void testImageIO() throws IOException {
        BufferedImage image = ImageIO.read(new File("src/test/resources/imageio.png"));
        Assertions.assertTrue(ImageIO.write(image, "jpg", File.createTempFile("imageio", ".jpg")));
    }

}