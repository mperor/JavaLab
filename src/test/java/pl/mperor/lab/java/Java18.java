package pl.mperor.lab.java;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.TestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

/// Java 18™ (March 2022)
/// [JDK 18](https://openjdk.org/projects/jdk/18)
///
/// - STANDARD FEATURES:
///     - 400:	UTF-8 by Default
///     - 408:	Simple Web Server
///     - 421:	Deprecate Finalization for Removal
///     - 418:	Internet-Address Resolution SPI (service-provider interface)
///     - 413:	Code Snippets in Java API Documentation
///     - 416:	Reimplement Core Reflection with Method Handles
///
/// - PREVIEW & INCUBATOR:
///     - 419:	Foreign Function & Memory API (Second Incubator)
///     - 420:	Pattern Matching for switch (Second Preview)
///     - 417:	Vector API (Third Incubator)
public class Java18 {

    @Test
    public void testDefaultCharsetIsUTF8() throws IOException {
        System.out.printf("Running Java Version: %d, file.encoding=%s, native.encoding=%s%n",
                Runtime.version().feature(),
                System.getProperty("file.encoding"),
                System.getProperty("native.encoding")
        );

        Charset defaultCharset = Charset.defaultCharset();
        Assertions.assertEquals("UTF-8", defaultCharset.name());

        var utf8Reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("encoding/fileUTF8.txt")));
        var win1250Reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("encoding/fileWin1250.txt"), Charset.forName("windows-1250")));

        try (utf8Reader; win1250Reader) {
            Assertions.assertEquals(utf8Reader.readLine(), win1250Reader.readLine());
        }
    }

    @Test
    public void testSimpleWebServer() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8018), 0);
        server.createContext("/hello", exchange -> {
            String response = "Hello World!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        server.start();
        Assertions.assertNotNull(server);
        Assertions.assertEquals("Hello World!", curlResponse("http://127.0.0.1:8018/hello"));

        server.stop(0);
    }

    private static String curlResponse(String url) throws IOException, InterruptedException {
        var curlProcess = new ProcessBuilder("curl", url).start();
        String result;
        try (var reader = curlProcess.inputReader()) {
            result = reader.readLine();
        }
        curlProcess.waitFor();
        curlProcess.destroy();
        return result;
    }

    @SuppressWarnings("removal")
    @Test
    public void testFinalizeDeprecated() throws Throwable {
        var out = TestUtils.setTempSystemOut();

        // warning: [removal] finalize() in Object has been deprecated and marked for removal
        var object = new Object() {
            @Override
            protected void finalize() throws Throwable {
                super.finalize();
                System.out.print("finalizing ...");
            }
        };
        object.finalize();
        Assertions.assertEquals("finalizing ...", out.all());
        TestUtils.resetSystemOut();
    }

    @Test
    public void testInetAddressResolution() throws UnknownHostException {
        InetAddress localhost = InetAddress.getByName("localhost");
        Assertions.assertTrue(localhost.isLoopbackAddress());

        InetAddress google = InetAddress.getByName("google.com");
        Assertions.assertNotNull(google.getAddress());
    }

    @Test
    public void testSnippet() {
        Assertions.assertNotNull(new JavadocSample());
    }

    /**
     * This is a doc for {@code JavadocSample}.
     * Code snippet:
     * {@snippet :
     * JavadocSample js = new JavadocSample();}
     */
    private class JavadocSample {
    }

}