package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

/// Java 25 (September 2025)
/// [JDK 25](https://openjdk.org/projects/jdk/25)
///
/// - STANDARD FEATURES:
///     - 503:	Remove the 32-bit x86 Port
///     - 506:	Scoped Values
///     - 510:	Key Derivation Function API
///     - 511:	Module Import Declarations
///     - 512:	Compact Source Files and Instance Main Methods
///     - 513:	Flexible Constructor Bodies
///     - 514:	Ahead-of-Time Command-Line Ergonomics
///     - 515:	Ahead-of-Time Method Profiling
///     - 518:	JFR Cooperative Sampling
///     - 519:	Compact Object Headers
///     - 520:	JFR Method Timing & Tracing
///     - 521:	Generational Shenandoah
///
/// - PREVIEW & INCUBATOR:
///     - 470:	PEM Encodings of Cryptographic Objects (Preview)
///     - 502:	Stable Values (Preview)
///     - 505:	Structured Concurrency (Fifth Preview)
///     - 507:  Primitive Types in Patterns, instanceof, and switch (Third Preview)
///     - 508:	Vector API (Tenth Incubator)
///     - 509:	JFR CPU-Time Profiling (Experimental)
public class Java25 {

    @Test
    public void testPrintFromIOClass() {
        var out = TestUtils.setTempSystemOut();
        IO.println("Hello World!"); // ⇒ System.out.println("Hello World!");
        Assertions.assertEquals("Hello World!" + System.lineSeparator(), out.all());
        TestUtils.resetSystemOut();
    }

}