package pl.mperor.lab.java;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.TestUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(nullValidator.test(null));

        var out = TestUtils.setTempSystemOut();
        Consumer<String> systemPrinter = System.out::print;
        systemPrinter.accept("printed");
        Assertions.assertEquals("printed", out.all());
        TestUtils.resetSystemOut();

        Supplier<Double> randomGenerator = Math::random;
        assertTrue(randomGenerator.get() < 1.0);

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

}