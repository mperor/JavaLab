package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.generic.Box;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Java 1.7 (July 2011)
 */
public class Java7 {

    @Test
    public void testBinaryLiterals() {
        int binary = 0b1010;
        Assertions.assertEquals(10, binary);
    }

    @Test
    public void testUnderscoresInNumericLiterals() {
        int thousand = 1_000;
        Assertions.assertEquals(1_000_000, thousand * thousand);
    }

    @Test
    public void testDiamondOperator() {
        Box<String> stringBox = new Box<>(); // diamond '<>'
        // same as ... = new Box<String>();
        stringBox.setContent("abc");
        Assertions.assertEquals("abc", stringBox.getContent());
    }

    @Test
    public void testTryWithResourcesAndSuppressedException() {
        var rootException = Assertions.assertThrows(IllegalStateException.class, () -> {
            try (FailingResource resource = new FailingResource()) {
                throw new IllegalStateException("State inside try block failed!");
            }
        });
        Assertions.assertEquals("State inside try block failed!", rootException.getMessage());
        Throwable[] suppressed = rootException.getSuppressed();
        Assertions.assertEquals(1, suppressed.length);
        Assertions.assertEquals("Closing resource failed", suppressed[0].getMessage());
    }

    class FailingResource implements Closeable {
        @Override
        public void close() throws IOException {
            throw new IOException("Closing resource failed");
        }
    }

    @Test
    public void testMultiCatchException() {
        Throwable caughtException = Assertions.assertThrows(Exception.class, () -> {
            try {
                int dividedByZero = 1 / 0;
            } catch (IllegalStateException | ArithmeticException e) {
                throw new Exception("Caught Exception", e);
            }
        }).getCause();
        Assertions.assertInstanceOf(ArithmeticException.class, caughtException);
    }

    @Test
    public void testStringInSwitch() {
        Assertions.assertEquals('+', getSign("add"));
        Assertions.assertEquals('+', getSign("plus"));
        Assertions.assertEquals('-', getSign("subtract"));
        Assertions.assertEquals('-', getSign("minus"));
        Assertions.assertEquals('?', getSign(""));
    }

    private char getSign(String command) {
        switch (command) {
            case "add":
            case "plus":
                return '+';
            case "subtract":
            case "minus":
                return '-';
            default:
                return '?';
        }
    }

    @Test
    public void testNewInputOutputV2AkaNIO2() throws IOException {
        File nio2File = new File("src/test/resources/nio2.txt");
        Assertions.assertEquals("Hello NIO2!", Files.readString(nio2File.toPath()));

        Path testDirPath = Files.createTempDirectory("test");
        Assertions.assertTrue(Files.isDirectory(testDirPath), "Directory should be created");
        Path testFilePath = testDirPath.resolve("test.txt");

        Files.write(testFilePath, "Test".getBytes());
        Assertions.assertTrue(Files.isRegularFile(testFilePath), "File should be created");
        try (var reader = Files.newBufferedReader(testFilePath)) {
            Assertions.assertEquals("Test", reader.readLine());
        }

        Assertions.assertTrue(Files.deleteIfExists(testFilePath), "File should be deleted");
        Assertions.assertTrue(Files.deleteIfExists(testDirPath), "Directory is empty and should be deleted");
    }

    @Test
    public void testForkJoinFibonacci() {
        int proc = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Assertions.assertEquals(proc - 1, pool.getParallelism());

        int[] fibonacciResults = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55};
        for (int i = 0; i < fibonacciResults.length; i++) {
            Assertions.assertEquals(fibonacciResults[i], pool.invoke(new FibonacciTask(i)));
        }
    }

    class FibonacciTask extends RecursiveTask<Integer> {
        private final int n;

        FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            FibonacciTask f1 = new FibonacciTask(n - 1);
            FibonacciTask f2 = new FibonacciTask(n - 2);
            f1.fork();
            return f2.compute() + f1.join();
        }
    }

}
