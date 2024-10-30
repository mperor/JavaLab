package pl.mperor.lab.java.data.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpressionTypeTest {

    @Test
    public void testExpressionVsStatement() {
        byte b = 1;
        Assertions.assertEquals(2, b + b, "statement");
        int expression = b + b;
        Assertions.assertEquals(2, expression, "expression");
    }

    @Test
    public void testTypeOfArithmeticExpression() {
        byte b = 1;
        Assertions.assertInstanceOf(Integer.class, b + b);
        short s = 2;
        Assertions.assertInstanceOf(Integer.class, s + s);
        Assertions.assertInstanceOf(Long.class, 1 + 1L);

        Assertions.assertInstanceOf(Float.class, 1.0f + 1.0f);
        Assertions.assertInstanceOf(Double.class, 1 + 1.0);
        Assertions.assertInstanceOf(Double.class, 1.0f + 1.0);
        Assertions.assertInstanceOf(String.class, "" + 1.0);
    }

}
