package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Java 1.9 (September 2017)
 */
public class Java9 {

    @Test
    public void testNewHTTPClientAPI_getMethod() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
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
    public void testNewHTTPClientAPI_postMethod() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
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
}
