package pl.mperor.lab.java;

import jdk.jfr.Configuration;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.consumer.RecordingStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/// Java 14™ (March 2020)
/// [JDK 14](https://openjdk.org/projects/jdk/14)
///
/// - STANDARD FEATURES:
///     - 361:	Switch Expressions (Standard)
///     - 358:	Helpful NullPointerExceptions
///     - 367:	Remove the Pack200 Tools and API
///     - 349:	JFR Event Streaming (`-XX:StartFlightRecording:filename=events.jfr`) [JFAPI](https://docs.oracle.com/en/java/javase/23/jfapi)
///     - 363:	Remove the Concurrent Mark Sweep (CMS) Garbage Collector (`-XX:+UseConcMarkSweepGC`)
///     - 345:	NUMA-Aware Memory Allocation for G1 (`+XX:+UseNUMA`)
///     - 352:	Non-Volatile Mapped Byte Buffers
///     - 364:	ZGC on macOS
///     - 365:	ZGC on Windows
///     - 362:	Deprecate the Solaris and SPARC Ports
///     - 366:	Deprecate the ParallelScavenge + SerialOld GC Combination (`-XX:-UseParallelOldGC`)
///
/// - PREVIEW & INCUBATOR:
///     - 359:	Records (Preview)
///     - 305:	Pattern Matching for instanceof (Preview)
///     - 368:	Text Blocks (Second Preview)
///     - 343:	Packaging Tool `jpackage` (Incubator)
///     - 370:	Foreign-Memory Access API (Incubator)
public class Java14 {

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
    public void testSwitchStatementVsSwitchExpression() {
        assertTrue(switchExpression(Switch.EXPRESSION));
        assertTrue(switchStatement(Switch.STATEMENT));
    }

    enum Switch {EXPRESSION, STATEMENT}

    private static boolean switchExpression(Switch s) {
        return switch (s) {
            case EXPRESSION -> true;
            default -> false;
        };
    }

    private static boolean switchStatement(Switch s) {
        switch (s) {
            case STATEMENT -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Test
    public void testHelpfulNullPointerExceptions() {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            String name = null;
            name.length();
        });
        assertTrue(exception.getMessage().contains("Cannot invoke \"String.length()\" because \"name\" is null"));
    }

    @Test
    public void testPack200ToolsAndAPIRemoved() {
        Assertions.assertThrows(ClassNotFoundException.class, () -> {
            Class.forName("java.util.jar.Pack200");
        });
    }

    @Test
    public void testThreadIsInterruptedAlwaysAvailable() {
        Thread neverStartedThread = new Thread();
        Assertions.assertFalse(neverStartedThread.isInterrupted());
        neverStartedThread.interrupt();
        Assertions.assertTrue(neverStartedThread.isInterrupted());
    }

    @Test
    public void testJFREvent() throws IOException, ParseException, InterruptedException {
        Configuration c = Configuration.getConfiguration("profile");
        try (RecordingStream rs = new RecordingStream(c)) {
            rs.onEvent("jdk.ThreadSleep", event -> event.getDuration().equals(Duration.ofSeconds(100)));
            rs.startAsync();

            System.out.println("Sleeping for 100ms...");
            Thread.sleep(100);
        }
    }

}