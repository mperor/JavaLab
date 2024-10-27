package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.text.ListFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// Java 22 (March 2024)
/// [JDK 22](https://openjdk.org/projects/jdk/22)
///
/// - STANDARD FEATURES:
///     - 454:	Foreign Function & Memory API
///     - 456:	Unnamed Variables & Patterns
///     - 458:	Launch Multi-File Source-Code Programs
///     - 423:	Region Pinning for G1
///
/// - PREVIEW & INCUBATOR:
///     - 461:	Stream Gatherers (Preview)
///     - 457:	Class-File API (Preview)
///     - 447:	Statements before super(...) (Preview)
///     - 459:	String Templates (Second Preview)
///     - 462:	Structured Concurrency (Second Preview)
///     - 463:	Implicitly Declared Classes and Instance Main Methods (Second Preview)
///     - 464:	Scoped Values (Second Preview)
///     - 460:	Vector API (Seventh Incubator)
public class Java22 {

    @Test
    public void testForeignFunction() throws Throwable {
        String str = "test";
        Arena arena = Arena.ofAuto();
        MemorySegment cString = arena.allocateFrom(str);
        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
        MethodHandle strlen = linker.downcallHandle(
                stdlib.find("strlen").orElseThrow(),
                FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
        );
        Assertions.assertEquals((long) str.length(), strlen.invoke(cString));
    }

    @Test
    public void testMemoryAPI() {
        var intArrayLayout = MemoryLayout.sequenceLayout(10, ValueLayout.JAVA_INT);
        try (var arena = Arena.ofConfined()) {
            var segment = arena.allocate(intArrayLayout);
            for (int i = 0; i < intArrayLayout.elementCount(); i++) {
                segment.setAtIndex(ValueLayout.JAVA_INT, i, i);
                Assertions.assertEquals(i, segment.getAtIndex(ValueLayout.JAVA_INT, i));
            }
        }
    }

    @Test
    public void testUnnamedVariables() {
        String repeated = IntStream.rangeClosed(1, 3)
                .mapToObj(_ -> "Java 23!")
                .collect(Collectors.joining());
        Assertions.assertEquals("Java 23!".repeat(3), repeated);

        Comparator<Integer> uselessComparator = (_, _) -> 1;
        int[] sorted = Stream.of(1, 2, 3)
                .sorted(uselessComparator)
                .mapToInt(Integer::intValue)
                .toArray();
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, sorted);

        Assertions.assertDoesNotThrow(() -> muteException(() -> 1 / 0));
        Assertions.assertDoesNotThrow(() -> muteException(() -> null));
        Assertions.assertDoesNotThrow(() -> muteException(() -> {
            throw new NullPointerException();
        }));
    }

    private static <T> T muteException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception _) {
            return null;
        }
    }

    @Test
    public void testUnnamedPatterns() {
        Assertions.assertTrue(readyToGo(new GreenLight()));
        Assertions.assertTrue(readyToGo(new YellowLight()));
        Assertions.assertFalse(readyToGo(new RedLight()));
    }

    public boolean readyToGo(TrafficLight light) {
        return switch (light) {
            case GreenLight _, YellowLight _ -> true;
            case RedLight _ -> false;
        };
    }

    sealed abstract class TrafficLight permits RedLight, YellowLight, GreenLight {}

    final class RedLight extends TrafficLight {}

    final class YellowLight extends TrafficLight {}

    final class GreenLight extends TrafficLight {}

    @Test
    public void testForbiddenForUnnamed() {
        var unnamed = new Unnamed();
        Assertions.assertEquals("this is ok!", unnamed.__);
        Assertions.assertDoesNotThrow(() -> unnamed.works(0));
    }

    private static class Unnamed {
        //private String _ = "this is forbidden!";

        private String __ = "this is ok!";

        //private void forbidden(int _) {
        //}
        private void works(int __) {
        }
    }

    @Test
    public void testSystemEncodingProperties() {
        Assertions.assertNotNull(System.getProperty("stdout.encoding"));
        Assertions.assertNotNull(System.getProperty("stderr.encoding"));
    }

    @Test
    public void testListFormat() {
        String formatted = ListFormat.getInstance(Locale.UK, ListFormat.Type.STANDARD, ListFormat.Style.FULL)
                .format(List.of("One", "Two", "Three"));
        Assertions.assertEquals("One, Two and Three", formatted);
    }
}