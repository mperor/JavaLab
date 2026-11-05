package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    @Test
    public void testWindowSlidingVsWindowFixed() {
        var fixed = IntStream.rangeClosed(1, 5)
                .boxed()
                .gather(Gatherers.windowFixed(2))
                .toList();

        var sliding = IntStream.rangeClosed(1, 5)
                .boxed()
                .gather(Gatherers.windowSliding(2))
                .toList();

        // windowFixed partitions the stream: windows never overlap and the last one may be shorter.
        Assertions.assertEquals(List.of(List.of(1, 2), List.of(3, 4), List.of(5)), fixed);
        // windowSliding moves one element at a time: every full-size window is kept, so windows overlap.
        Assertions.assertEquals(
                List.of(List.of(1, 2), List.of(2, 3), List.of(3, 4), List.of(4, 5)), sliding);
    }

    @Test
    public void testFoldVsReduce() {
        var fold = Stream.of(1, 2, 3, 4)
                .gather(Gatherers.fold(() -> "", (acc, n) -> acc + n))
                .findFirst()
                .orElseThrow();

        // the 3-arg reduce computes the same left-to-right accumulation, but still needs a
        // combiner because reduce is defined to also support parallel streams.
        var reduce = Stream.of(1, 2, 3, 4).reduce("", (acc, n) -> acc + n, String::concat);

        Assertions.assertEquals("1234", fold);
        Assertions.assertEquals(reduce, fold);
    }

    @Test
    public void testScanRunningTotals() {
        // unlike fold, which emits only the final accumulated value, scan emits every
        // intermediate result as it is produced.
        var runningTotals = Stream.of(1, 2, 3, 4)
                .gather(Gatherers.scan(() -> 0, Integer::sum))
                .toList();

        Assertions.assertEquals(List.of(1, 3, 6, 10), runningTotals);
    }

    @Test
    public void testMapConcurrentPreservesEncounterOrder() {
        // mapConcurrent runs the mapper for each element on its own virtual thread, but still
        // buffers the results so the output keeps the input's encounter order.
        var delaysMillis = List.of(300, 100, 200, 50);

        var result = delaysMillis.stream()
                .gather(Gatherers.mapConcurrent(delaysMillis.size(), Java24::sleepAndReturn))
                .toList();

        Assertions.assertEquals(delaysMillis, result);
    }

    private static int sleepAndReturn(int delayMillis) {
        try {
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return delayMillis;
    }

    @Test
    public void testCreateGatherer() {
        var result = Stream.of("one", "two", "three", "four", "five")
                .gather(distinctBy(String::length))
                .toList();

        Assertions.assertEquals(List.of("one", "three", "four"), result);
    }

    /// Keeps only the first element for each key produced by `extractor`.
    private static <T, P> Gatherer<T, ?, T> distinctBy(Function<? super T, ? extends P> extractor) {
        return Gatherer.ofSequential(HashSet<P>::new, Gatherer.Integrator.ofGreedy(
                (seen, element, downstream) -> {
                    if (seen.add(extractor.apply(element))) {
                        return downstream.push(element);
                    }
                    return true;
                }
        ));
    }

    @Test
    public void testManyVirtualThreadsBlockingInsideSynchronizedDoNotPinCarriers() {
        int threadCount = 1000;
        int sleepMillis = 80;
        int carriers = Runtime.getRuntime().availableProcessors();

        long elapsedMillis = TestUtils.measureExecutionTimeMillis(() -> {
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < threadCount; i++) {
                    executor.submit(() -> {
                        // each thread uses its own monitor: this is about pinning while
                        // blocked, not about lock contention.
                        synchronized (new Object()) {
                            Thread.sleep(sleepMillis);
                        }
                        return null;
                    });
                }
            }
        });

        // before JEP 491, blocking inside `synchronized` pinned a virtual thread to its carrier,
        // so `threadCount` of them could run only `carriers` at a time: roughly
        // threadCount / carriers * sleepMillis in total. Since JDK 24 the carrier is released
        // instead, so they all run close to concurrently, in roughly one sleepMillis.
        long pinnedEstimateMillis = (long) Math.ceil((double) threadCount / carriers) * sleepMillis;
        Assertions.assertTrue(elapsedMillis < pinnedEstimateMillis / 3,
                "expected %dms to stay well under the pinned estimate of %dms".formatted(elapsedMillis, pinnedEstimateMillis));
    }

    // JEP 491 has one remaining pinning case: a virtual thread that blocks inside `synchronized`
    // while also on a native (JNI) frame still pins its carrier, because the JVM cannot unmount
    // a thread across native stack frames. That case needs a JNI call to trigger, so it has no
    // simple JUnit-level test here.

    @Test
    public void testSynchronizedBlockedThreadIgnoresInterrupt() throws InterruptedException {
        Object monitor = new Object();
        var holderEntered = new CountDownLatch(1);
        var release = new CountDownLatch(1);
        Thread holder = Thread.ofVirtual().start(() -> {
            synchronized (monitor) {
                holderEntered.countDown();
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        holderEntered.await();

        var acquired = new AtomicBoolean(false);
        Thread waiter = Thread.ofVirtual().start(() -> {
            synchronized (monitor) {
                acquired.set(true);
            }
        });
        Thread.sleep(100); // give the waiter time to actually block entering the monitor
        waiter.interrupt();
        Thread.sleep(100);

        // interrupting a thread that is blocked entering a `synchronized` block has no effect:
        // it keeps waiting instead of throwing InterruptedException.
        Assertions.assertFalse(acquired.get());
        Assertions.assertEquals(Thread.State.BLOCKED, waiter.getState());

        release.countDown();
        waiter.join();
        holder.join();
        Assertions.assertTrue(acquired.get());
    }

    @Test
    public void testReentrantLockIsInterruptibleUnlikeSynchronized() throws InterruptedException {
        var lock = new ReentrantLock();
        lock.lock();

        var interrupted = new AtomicBoolean(false);
        Thread waiter = Thread.ofVirtual().start(() -> {
            try {
                lock.lockInterruptibly();
                lock.unlock();
            } catch (InterruptedException e) {
                interrupted.set(true);
            }
        });
        Thread.sleep(100);
        waiter.interrupt();
        waiter.join();

        // unlike synchronized (see above), ReentrantLock#lockInterruptibly gives the waiting
        // thread a way out: it throws as soon as it is interrupted, instead of waiting for the
        // lock forever. That, plus tryLock, fairness and multiple Conditions, is still a reason
        // to reach for it over synchronized.
        Assertions.assertTrue(interrupted.get());
        lock.unlock();
    }
}
