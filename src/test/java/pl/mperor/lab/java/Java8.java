package pl.mperor.lab.java;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.TestUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.*;

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
        Consumer<String> printer = System.out::print;
        printer.accept("printed");
        Assertions.assertEquals("printed", out.all());
        TestUtils.resetSystemOut();

        Supplier<Double> randomGenerator = Math::random;
        Assertions.assertTrue(randomGenerator.get() < 1.0);

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

        Assertions.assertTrue(Testable.class.isAnnotationPresent(FunctionalInterface.class));

        long abstractMethodCount = Arrays.stream(Testable.class.getMethods())
                .map(Method::getModifiers)
                .filter(Modifier::isAbstract)
                .count();
        Assertions.assertEquals(1, abstractMethodCount);
    }

    @FunctionalInterface
    public interface Testable {
        void test();
    }

}