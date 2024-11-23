package pl.mperor.lab.java.statement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControlStatementTest {

    @Test
    public void testIfElseStatement() {
        Assertions.assertEquals(1, defaultIfNull(null, 1));
        Assertions.assertEquals(-1, defaultIfNull(-1, 1));
    }

    public static <T> T defaultIfNull(T nullable, T ifNull) {
        if (nullable != null) {
            return nullable;
        }

        return ifNull;
    }

    @Test
    public void testSwitchBlock() {
        Assertions.assertEquals(2, calculateByOperator("+", 1, 1));
        Assertions.assertEquals(0, calculateByOperator("-", 1, 1));
        Assertions.assertEquals(4, calculateByOperator("*", 2, 2));
        Assertions.assertEquals(2, calculateByOperator("/", 4, 2));
        Assertions.assertThrows(IllegalStateException.class, () -> calculateByOperator("?", 1, 1));
    }

    public static int calculateByOperator(String operator, int a, int b) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return b != 0 ? a / b : 0;
            default:
                throw new IllegalStateException("Invalid operator: " + operator);
        }
    }

}
