package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Java 1.9 (September 2017)
 */
public class Java9 {

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
    public void testProcessAPI() throws IOException {
        ProcessHandle self = ProcessHandle.current();
        ProcessHandle.Info info = self.info();
        Assertions.assertNotNull(info);
        Assertions.assertTrue(info.command().isPresent());

        Process process = new ProcessBuilder("java", "-version").start();
        Assertions.assertTrue(process.isAlive());
        Assertions.assertTrue(process.pid() != -1);
        process.destroy();
    }

    @Test
    public void testJShell() throws IOException, InterruptedException {
        Process process = new ProcessBuilder("jshell")
                .start();
        Assertions.assertTrue(process.isAlive());

        try (var reader = process.inputReader();
             var writer = new PrintWriter(process.outputWriter())) {
            writer.println("System.out.println(\"Hello from JShell!\");");
            writer.println("/exit");
            writer.flush();

            String lastLineBeforeExit = reader.lines()
                    .takeWhile(s -> !s.contains("Goodbye"))
                    .reduce("", (first, second) -> second);
            Assertions.assertEquals("Hello from JShell!", lastLineBeforeExit);
        }
        Assertions.assertEquals(0, process.waitFor());
        process.destroy();
    }

    @Test
    public void testPrivateInterfaceMethod() {
        InterfaceJava9 myInterface = new InterfaceJava9() {};
        Assertions.assertEquals("Private logic in public method!", myInterface.publicMethod());
    }

    public interface InterfaceJava9 {
        default String publicMethod() {
            return privateMethod() + " in public method!";
        }

        private String privateMethod() {
            return "Private logic";
        }
    }

    @Test
    public void testCollectionFactoryMethods() {
        Map<String, Integer> byFactoryMethodMap = Map.of("one", 1, "two", 2, "three", 3);

        // Old style combination of `new HashMap<>()` and `put()`
        Map<String, Integer> classicalMap = new HashMap<>();
        classicalMap.put("one", 1);
        classicalMap.put("two", 2);
        classicalMap.put("three", 3);
        Assertions.assertEquals(byFactoryMethodMap, classicalMap);

        // Double Brace Initialization
        Map<String, Integer> doubleBraceInitializedMap = new HashMap<>() {{
            put("one", 1);
            put("two", 2);
            put("three", 3);
        }};
        Assertions.assertEquals(byFactoryMethodMap, doubleBraceInitializedMap);

        // Single entry map
        Map<String, Integer> singleEntryMap = Collections.singletonMap("one", 1);
        Assertions.assertEquals(byFactoryMethodMap.get("one"), singleEntryMap.get("one"));
    }

    @Test
    public void testTryWithResourcesWithJava9Syntax() throws IOException {
        File tempFile = File.createTempFile("Java9", "txt");
        // Declare and initialize the resource before the try block
        FileWriter writer = new FileWriter(tempFile);
        try (writer) {
            writer.write("Java 9 syntax for try-with-resources!");
        }
        IOException flushException = Assertions.assertThrows(IOException.class, () -> writer.flush());
        Assertions.assertEquals("Stream closed", flushException.getMessage());
        Assertions.assertEquals("Java 9 syntax for try-with-resources!", Files.readString(tempFile.toPath()));
        Assertions.assertTrue(Files.deleteIfExists(tempFile.toPath()));
    }

}
