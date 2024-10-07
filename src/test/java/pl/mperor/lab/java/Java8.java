package pl.mperor.lab.java;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.TestUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Java 1.8 (March 2014)
 */
public class Java8 {

    @Test
    public void testLambdaExpression() {
        var out = TestUtils.setTempSystemOut();
        Runnable anonymousInnerClass = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World from anonymous inner class!");
            }
        };
        Runnable lambdaExpression = () -> System.out.println("Hello World from Lambda!");

        anonymousInnerClass.run();
        lambdaExpression.run();

        Assertions.assertEquals("Hello World from anonymous inner class!", out.lines().getFirst());
        Assertions.assertEquals("Hello World from Lambda!", out.lines().getSecond());

        Assertions.assertInstanceOf(Runnable.class, anonymousInnerClass);
        Assertions.assertInstanceOf(Runnable.class, lambdaExpression);
        TestUtils.resetSystemOut();
    }

    @Test
    public void testCoreFunctionalInterfaces() {
        Predicate<?> nullValidator = Objects::isNull;
        Assertions.assertTrue(nullValidator.test(null));

        var out = TestUtils.setTempSystemOut();
        Consumer<String> systemPrinter = System.out::print;
        systemPrinter.accept("printed");
        Assertions.assertEquals("printed", out.all());
        TestUtils.resetSystemOut();

        Supplier<Double> randomGenerator = Math::random;
        Double random = randomGenerator.get();
        Assertions.assertTrue(random >= 0.0 && random < 1.0);

        UnaryOperator<Integer> absoluteUpdater = Math::abs;
        Assertions.assertEquals(1, absoluteUpdater.apply(-1));

        Function<String, Integer> numberParser = Integer::parseInt;
        Assertions.assertEquals(1, numberParser.apply("1"));
    }

    @Test
    public void testCustomFunctionalInterface() {
        Testable testable = () -> {
        };
        testable.test();
        // same as Runnable runnable = () -> {};

        assertFunctionalInterface(Testable.class);
    }

    private static void assertFunctionalInterface(Class<?> clazz) {
        if (!clazz.isInterface()) {
            Assertions.fail("Clazz is not an interface!");
        }

        long abstractMethodCount = Arrays.stream(clazz.getMethods())
                .map(Method::getModifiers)
                .filter(Modifier::isAbstract)
                .count();

        Assertions.assertTrue(clazz.isAnnotationPresent(FunctionalInterface.class) || 1 == abstractMethodCount,
                "Functional interface should contain exactly one abstract method, but @FunctionalInterface is optional!");
    }

    @FunctionalInterface
    public interface Testable {
        void test();
    }

    @Test
    public void testDefaultAndStaticMethodsInInterface() throws NoSuchMethodException {
        Tester tester = () -> new Testable[]{
                () -> System.out.println("First test"),
                () -> System.out.println("Second test"),
        };
        var out = TestUtils.setTempSystemOut();
        tester.run();
        Assertions.assertEquals("First test", out.lines().getFirst());
        Assertions.assertEquals("Second test", out.lines().getSecond());
        TestUtils.resetSystemOut();

        Method defaultMethod = Tester.class.getMethod("run");
        Assertions.assertTrue(defaultMethod.isDefault());

        Method staticMethod = Tester.class.getMethod("checkAll", Testable[].class);
        Assertions.assertTrue(Modifier.isStatic(staticMethod.getModifiers()));

        assertFunctionalInterface(Tester.class);
    }

    public interface Tester {

        Testable[] getTests();

        default void run() {
            checkAll(getTests());
        }

        static void checkAll(Testable... testable) {
            Arrays.stream(testable).forEach(Testable::test);
        }
    }

    @Test
    public void testOptional() {
        Optional<String> empty = Optional.empty();
        Assertions.assertTrue(empty.isEmpty());
        Assertions.assertThrows(NoSuchElementException.class, () -> empty.get());

        Optional<String> filled = Optional.of("Java 8");
        Assertions.assertTrue(filled.isPresent());
        Assertions.assertEquals("Java 8", filled.get());
        filled.ifPresent(content -> Assertions.assertEquals("Java 8", content));
    }

    @Test
    public void testMethodReferences() {
        Runnable staticMethod = Reference::staticMethod;
        staticMethod.run();

        Reference instance = new Reference();
        Runnable particularObjectInstanceMethod = instance::instanceMethod;
        particularObjectInstanceMethod.run();

        Supplier<Reference> constructorMethod = Reference::new;
        Assertions.assertNotNull(constructorMethod.get());

        Consumer<Reference> arbitraryObjectInstanceMethod = Reference::instanceMethod;
        arbitraryObjectInstanceMethod.accept(new Reference());
    }

    public class Reference {

        public void instanceMethod() {
        }

        public static void staticMethod() {
        }
    }

    @Test
    public void testStreamsAPI() {
        // Stream Creation
        Assertions.assertInstanceOf(Stream.class, List.of(1, 2, 3).stream());
        Assertions.assertInstanceOf(IntStream.class, Arrays.stream(new int[]{1, 2, 3}));
        Assertions.assertInstanceOf(Stream.class, Stream.of(1, 2, 3));

        // Intermediate Operations
        Stream<String> intermediateOperationPipeline = List.of("c", "b", "a", "").stream()
                .filter(s -> s.matches("^\\w$"))
                .map(String::toUpperCase)
                .sorted();
        Assertions.assertInstanceOf(Stream.class, intermediateOperationPipeline);
        Assertions.assertEquals("ABC", intermediateOperationPipeline.collect(Collectors.joining()));
        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, intermediateOperationPipeline::count);
        Assertions.assertEquals("stream has already been operated upon or closed", exception.getMessage());

        // Terminal Operations
        Assertions.assertEquals(3, IntStream.rangeClosed(1, 3).count());
        Assertions.assertEquals(6, IntStream.rangeClosed(1, 3).sum());
        Assertions.assertEquals(6, IntStream.rangeClosed(1, 3).reduce(1, (a, b) -> a * b));
        Assertions.assertEquals(List.of(1, 2, 3), IntStream.rangeClosed(1, 3).boxed().collect(Collectors.toList()));
        Assertions.assertEquals(OptionalInt.of(3), IntStream.rangeClosed(1, 3).max());
    }

    @Test
    public void testNewDateAndTimeAPI() {
        // same as DateTimeFormatter.ISO_LOCAL_DATE.parse(...)
        LocalDate releaseJava8Date = LocalDate.parse("2014-03-18");
        Assertions.assertEquals(LocalDate.of(2014, Month.MARCH, 18), releaseJava8Date);

        LocalTime noonTime = LocalTime.of(12, 0, 0);
        Assertions.assertEquals(LocalTime.NOON, noonTime);

        // same as DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(...)
        LocalDateTime twoThousand = LocalDateTime.parse("2000-01-01T00:00:00");
        Assertions.assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0, 0), twoThousand);

        Period betweenJava1And8 = Period.between(LocalDate.of(1996, Month.JANUARY, 23), LocalDate.of(2014, Month.MARCH, 8));
        Assertions.assertEquals(18, betweenJava1And8.getYears());

        Duration betweenMidnightAndNoon = Duration.between(LocalTime.MIDNIGHT, LocalTime.NOON);
        Assertions.assertEquals(12, betweenMidnightAndNoon.toHours());

        Instant initialInstant = Instant.ofEpochSecond(0);
        Assertions.assertEquals("1970-01-01T00:00:00Z", initialInstant.toString());
        Assertions.assertEquals(60, initialInstant.plusSeconds(60).getEpochSecond());
    }

}