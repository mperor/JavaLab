package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.MethodModel;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashSet;
import java.util.List;
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
    public void testParseListsMethodsAndFields() throws IOException {
        var classModel = ClassFile.of().parse(classBytesOf(SampleTarget.class));

        var methodNames = classModel.methods().stream()
                .map(method -> method.methodName().stringValue())
                .toList();
        var fieldNames = classModel.fields().stream()
                .map(field -> field.fieldName().stringValue())
                .toList();

        // the compiler-generated constructor shows up as a plain method named "<init>".
        Assertions.assertTrue(methodNames.containsAll(List.of("<init>", "add", "greet")));
        Assertions.assertEquals(List.of("bonus"), fieldNames);
    }

    /// A tiny, unused-looking helper class: its only purpose is to be parsed/transformed as raw bytecode below.
    private static class SampleTarget {
        private final int bonus;

        SampleTarget(int bonus) {
            this.bonus = bonus;
        }

        int add(int a, int b) {
            return a + b + bonus;
        }

        String greet(String name) {
            return "Hello, " + name;
        }
    }

    private static byte[] classBytesOf(Class<?> type) throws IOException {
        var resourceName = type.getName().replace('.', '/') + ".class";
        try (var in = type.getClassLoader().getResourceAsStream(resourceName)) {
            return in.readAllBytes();
        }
    }

    @Test
    public void testBuildClassAtRuntimeAndInvoke() throws Throwable {
        var generatedClass = ClassDesc.of("pl.mperor.lab.java.GeneratedAnswer");
        var classBytes = ClassFile.of().build(generatedClass, classBuilder -> classBuilder
                .withFlags(ClassFile.ACC_PUBLIC)
                .withSuperclass(ConstantDescs.CD_Object)
                .withMethodBody(
                        "answer",
                        MethodTypeDesc.of(ConstantDescs.CD_int),
                        ClassFile.ACC_PUBLIC | ClassFile.ACC_STATIC,
                        codeBuilder -> codeBuilder
                                .loadConstant(42)
                                .ireturn()));

        var lookup = MethodHandles.lookup().defineHiddenClass(classBytes, true);
        MethodHandle answer = lookup.findStatic(
                lookup.lookupClass(), "answer", MethodType.methodType(int.class));

        Assertions.assertEquals(42, (int) answer.invoke());
    }

    @Test
    public void testTransformDropsMethod() throws IOException {
        var originalModel = ClassFile.of().parse(classBytesOf(SampleTarget.class));

        ClassTransform dropAdd = ClassTransform.dropping(element ->
                element instanceof MethodModel method && method.methodName().stringValue().equals("add"));

        var transformedBytes = ClassFile.of().transformClass(originalModel, dropAdd);
        var transformedModel = ClassFile.of().parse(transformedBytes);

        var remainingMethods = transformedModel.methods().stream()
                .map(method -> method.methodName().stringValue())
                .toList();

        Assertions.assertFalse(remainingMethods.contains("add"));
        Assertions.assertTrue(remainingMethods.contains("greet"));
    }
}
