package pl.mperor.lab.java;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.TestUtils;

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

}