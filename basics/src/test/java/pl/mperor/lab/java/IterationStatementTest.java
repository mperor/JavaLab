package pl.mperor.lab.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class IterationStatementTest {

    private PrintStream original = System.out;
    private OutputStream out;

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(original);
    }

    @Test
    public void testWhileLoop() {
        var integers = List.of(1, 2, 3);
        var iterator = integers.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
        }
        Assertions.assertEquals("123", out.toString());
    }

    @Test
    public void testDoWhileLoop() {
        var connector = new FaultyConnector();
        do {
            connector.reset();
        } while (!connector.connect());
        Assertions.assertEquals(2, connector.resetCounter);
    }

    static class FaultyConnector {
        int doubleResetNeeded = 2;
        int resetCounter = 0;

        boolean connect() {
            return resetCounter >= doubleResetNeeded;
        }

        void reset() {
            resetCounter++;
        }
    }

    @Test
    public void testForLoop() {
        int[] integers = {1, 2, 3};
        for (int i = 0; i < integers.length; i++) {
            System.out.printf("(%d->%d)", i, integers[i]);
        }
        Assertions.assertEquals("(0->1)(1->2)(2->3)", out.toString());
    }

    @Test
    public void testEnhancedForLoopAkaForEachLoop() {
        var integers = List.of(1, 2, 3);
        for (Integer integer : integers) {
            System.out.println(integer);
        }
        Assertions.assertEquals("123", out.toString());
    }

    @Test
    public void testJumpStatements() {
        breakFromInfiniteLoop();
        printEvenNumbers(1, 2, 3, 4);
        Assertions.assertEquals("24", out.toString());
    }

    private void breakFromInfiniteLoop() {
        for (; ; ) {
            break;
        }
    }

    private void printEvenNumbers(int... numbers) {
        for (int number : numbers) {
            if (number % 2 != 0) {
                continue;
            }
            System.out.print(number);
        }
    }

    @Test
    public void testNestedLoop() {
        record Point(int x, int y) {
            @Override
            public String toString() {
                return "" + x + y;
            }
        }
        Point[][] matrix = {
                {new Point(0, 0), new Point(0, 1)},
                {new Point(1, 0), new Point(1, 1)}
        };

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.printf("(%s)", matrix[row][col]);
            }
        }

        Assertions.assertEquals("(00)(01)(10)(11)", out.toString());
    }

}
