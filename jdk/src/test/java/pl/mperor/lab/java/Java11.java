package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// Java 11™ (September 2018)
/// [JDK 11](https://openjdk.org/projects/jdk/11)
///
/// - STANDARD FEATURES:
///     - 321: HTTP Client (Standard)
///     - 323: Local-Variable Syntax for Lambda Parameters
///     - 332: Transport Layer Security (TLS) 1.3
///     - 320: Remove the Java EE and CORBA Modules
///     - 309: Dynamic Class-File Constants
///     - 181: Nest-Based Access Control
///     - 315: Improve Aarch64 Intrinsics
///     - 318: Epsilon: A No-Op Garbage Collector
///     - 327: Unicode 10
///     - 324: Key Agreement with Curve25519 and Curve448
///     - 328: Flight Recorder
///     - 329: ChaCha20 and Poly1305 Cryptographic Algorithms
///     - 330: Launch Single-File Source-Code Programs
///     - 331: Low-Overhead Heap Profiling
///     - 335: Deprecate the Nashorn JavaScript Engine
///     - 336: Deprecate the Pack200 Tools and API
///
/// - PREVIEW & INCUBATOR:
///     - 333: ZGC: A Scalable Low-Latency Garbage Collector (Experimental)
public class Java11 {

    @Test
    public void testNewHTTPClientAPI_getMethodSyncVsAsync() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://jsonplaceholder.typicode.com/posts/1"))
                .GET()
                .build();

        // Synchronous
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(response.body());

        // Asynchronous
        CompletableFuture<HttpResponse<String>> responseFuture =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        responseFuture.thenAccept(result -> {
            Assertions.assertEquals(200, result.statusCode());
            Assertions.assertNotNull(result.body());
        }).get();
    }

    @Test
    public void testNewHTTPClientAPI_postMethod() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://jsonplaceholder.typicode.com/posts"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(""" 
                        {
                            "title": "foo",
                            "body": "bar",
                            "userId": 1
                        }
                        """)
                ).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertNotNull(response.body());
    }

    @Test
    public void testStringAPIEnhancements() {
        Assertions.assertTrue("  ".isBlank());
        Assertions.assertFalse("Java 11".isBlank());

        Assertions.assertEquals("***", "  ***  ".strip());
        Assertions.assertEquals("***  ", "  ***  ".stripLeading());
        Assertions.assertEquals("  ***", "  ***  ".stripTrailing());

        String multiline = """
                First
                Second
                Third""";
        Assertions.assertEquals(3, multiline.lines().count());

        Assertions.assertEquals("DDD", "D".repeat(3));
    }

    @Test
    public void testReadAndWriteStringFromFiles() throws IOException {
        Path tempDirPath = Files.createTempDirectory("demo");
        Path demoFilePath = Files.createTempFile(tempDirPath, "demo", ".txt");
        Path filePath = Files.writeString(demoFilePath, "Secret");
        String fileContent = Files.readString(filePath);
        Assertions.assertEquals("Secret", fileContent);
    }

    @Test
    public void testPredicateNot() {
        String nonBlankJoined = Stream.of("a", "\n \n", "b", " ", "c")
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.joining());

        Assertions.assertEquals("abc", nonBlankJoined);
    }

    @Test
    public void testTransferTo() throws Exception {
        String data = "Java 11 InputStream transferTo method";

        try (ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            input.transferTo(output);

            String result = output.toString(StandardCharsets.UTF_8);
            Assertions.assertEquals(data, result);
        }
    }

    @Test
    public void testCollectionToArray() {
        Assertions.assertArrayEquals(new String[]{"x", "y", "z"}, List.of("x", "y", "z").toArray(String[]::new));
    }

    @Test
    public void testTransportLayerSecurity13() throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (var socket = (SSLSocket) factory.createSocket("google.com", 443)) {
            socket.setEnabledProtocols(new String[]{"TLSv1.3"});
            socket.setEnabledCipherSuites(new String[]{"TLS_AES_128_GCM_SHA256"});
            Assertions.assertTrue(socket.isConnected());
        }
    }

    @Test
    public void testJavaEERemoved() {
        Assertions.assertThrows(ClassNotFoundException.class, () -> Class.forName("javax.activation.DataHandler"));
        Assertions.assertThrows(ClassNotFoundException.class, () -> Class.forName("javax.xml.bind.JAXBContext"));
    }

}