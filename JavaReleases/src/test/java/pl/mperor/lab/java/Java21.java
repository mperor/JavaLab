package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/// Java 21™ (September 2023)
/// [JDK 21](https://openjdk.org/projects/jdk/21)
///
/// - STANDARD FEATURES:
///     - 444:	Virtual Threads
///     - 431:	Sequenced Collections
///     - 440:	Record Patterns
///     - 441:	Pattern Matching for switch
///     - 452:	Key Encapsulation Mechanism API
///     - 439:	Generational ZGC
///     - 451:	Prepare to Disallow the Dynamic Loading of Agents
///     - 449:	Deprecate the Windows 32-bit x86 Port for Removal
///
/// - PREVIEW & INCUBATOR:
///     - 430:	String Templates (Preview)
///     - 442:	Foreign Function & Memory API (Third Preview)
///     - 443:	Unnamed Patterns and Variables (Preview)
///     - 445:	Unnamed Classes and Instance Main Methods (Preview)
///     - 446:	Scoped Values (Preview)
///     - 453:	Structured Concurrency (Preview)
///     - 448:	Vector API (Sixth Incubator)
public class Java21 {

    @Test
    public void testVirtualThreads() throws InterruptedException {
        TestUtils.ReadableOut out = TestUtils.setTempSystemOut();
        // same as Thread.ofVirtual().start(() -> ...)
        Thread virtualThread = Thread.startVirtualThread(() ->
                System.out.print("Hello from Virtual Thread!")
        );
        virtualThread.join();
        TestUtils.resetSystemOut();

        Assertions.assertTrue(virtualThread.isDaemon() && virtualThread.isVirtual());
        Assertions.assertEquals(Thread.NORM_PRIORITY, virtualThread.getPriority());
        Assertions.assertEquals("Hello from Virtual Thread!", out.all());
    }


    @Test
    public void testVirtualVsPlatformThreads() {
        final int threadPool = 100;

        long virtualThreadsTime = TestUtils.measureExecutionTimeMillis(() -> {
                    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                        execute(threadPool, executor);
                    }
                }
        );

        long platformThreadsTime = TestUtils.measureExecutionTimeMillis(() -> {
                    try (var executor = Executors.newFixedThreadPool(threadPool)) {
                        execute(threadPool, executor);
                    }
                }
        );

        System.out.println("🌀 virtual threads: %s [ms]".formatted(virtualThreadsTime));
        System.out.println("🖥️ platform threads: %s [ms]".formatted(platformThreadsTime));
        Assertions.assertTrue(platformThreadsTime > virtualThreadsTime);
    }

    private static void execute(int threadPool, ExecutorService executor) {
        IntStream.rangeClosed(0, threadPool).forEach(i -> executor.submit(() -> {
            Thread.sleep(Duration.ofMillis(100));
            return i;
        }));
    }

    @Test
    public void testSequencedCollections() {
        List<String> letters = List.of("one", "two", "three");
        Assertions.assertEquals("one", letters.getFirst());
        Assertions.assertEquals("three", letters.getLast());
        Assertions.assertThrows(NoSuchElementException.class, Collections.emptyList()::getFirst);
        Assertions.assertInstanceOf(SequencedCollection.class, letters);
        Assertions.assertInstanceOf(SequencedCollection.class, new LinkedHashMap<>().keySet());
    }

    @Test
    public void testRecordPatternDeconstruct() {
        record Point(int x, int y) {}
        record LineSegment(Point start, Point end) {}
        Object obj = new LineSegment(new Point(0, 1), new Point(1, 2));

        if (obj instanceof LineSegment(Point(int x, int y), Point end)) {
            Assertions.assertEquals(0, x);
            Assertions.assertEquals(1, y);
            Assertions.assertEquals(new Point(1, 2), end);
        }
    }

    @Test
    public void testSwitchPatternMatching() {
        Assertions.assertEquals("Short String: abc", switchOverClasses("abc"));
        Assertions.assertEquals("Long String: longer than abc", switchOverClasses("longer than abc"));
        Assertions.assertEquals("int: 1", switchOverClasses(1));
        Assertions.assertEquals("long: 13", switchOverClasses(13L));
        Assertions.assertEquals("boolean: true", switchOverClasses(true));
        Assertions.assertEquals("null", switchOverClasses(null));
        record Person(String name, String surname) {
            @Override
            public String toString() {
                return "%s %s".formatted(name, surname);
            }
        }
        Assertions.assertEquals("Object: John Doe", switchOverClasses(new Person("John", "Doe")));
    }

    private static String switchOverClasses(Object obj) {
        return switch (obj) {
            case String s when s.length() <= 3 -> String.format("Short String: %s", s);
            case String s -> String.format("Long String: %s", s);
            case Integer i -> String.format("int: %d", i);
            case Long l -> String.format("long: %d", l);
            case Boolean b -> String.format("boolean: %s", b);
            case null -> "null";
            default -> "Object: " + obj;
        };
    }

}