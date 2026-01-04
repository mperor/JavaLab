package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

/// Java 24 (March 2025)
/// [JDK 24](https://openjdk.org/projects/jdk/24)
///
/// - STANDARD FEATURES:
///     - 472:	Prepare to Restrict the Use of JNI
///     - 475:	Late Barrier Expansion for G1
///     - 479:	Remove the Windows 32-bit x86 Port
///     - 483:	Ahead-of-Time Class Loading & Linking
///     - 484:	Class-File API
///     - 485:	Stream Gatherers
///     - 486:	Permanently Disable the Security Manager
///     - 490:	ZGC: Remove the Non-Generational Mode
///     - 491:	Synchronize Virtual Threads without Pinning
///     - 493:	Linking Run-Time Images without JMODs
///     - 496:	Quantum-Resistant Module-Lattice-Based Key Encapsulation Mechanism
///     - 497:	Quantum-Resistant Module-Lattice-Based Digital Signature Algorithm
///     - 498:	Warn upon Use of Memory-Access Methods in sun.misc.Unsafe
///     - 501:	Deprecate the 32-bit x86 Port for Removal
///
/// - PREVIEW & INCUBATOR:
///     - 404:	Generational Shenandoah (Experimental)
///     - 450:	Compact Object Headers (Experimental)
///     - 478:	Key Derivation Function API (Preview)
///     - 487:	Scoped Values (Fourth Preview)
///     - 488:	Primitive Types in Patterns, instanceof, and switch (Second Preview)
///     - 489:	Vector API (Ninth Incubator)
///     - 492:	Flexible Constructor Bodies (Third Preview)
///     - 494:	Module Import Declarations (Second Preview)
///     - 495:	Simple Source Files and Instance Main Methods (Fourth Preview)
public class Java24 {

    @Test
    public void testWindowFixedGatherer() {
        var result = IntStream.rangeClosed(1, 3)
                .boxed()
                .gather(Gatherers.windowFixed(2))
                .toList();

        Assertions.assertEquals(
                List.of(
                        List.of(1, 2),
                        List.of(3)
                ), result);
    }
}