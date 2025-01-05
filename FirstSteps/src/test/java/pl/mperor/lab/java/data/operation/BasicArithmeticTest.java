package pl.mperor.lab.java.data.operation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicArithmeticTest {

    @Test
    public void testDividingDoubleVsInt() {
        Assertions.assertTrue(3 == 16 / 5);
        Assertions.assertTrue(3.2 == (double) 16 / 5);
    }

    @Test
    public void testDividingDoubleByZero() {
        Assertions.assertEquals(Double.POSITIVE_INFINITY, 3.0 / 0);
        Assertions.assertEquals(Double.NEGATIVE_INFINITY, -3.0 / 0);
        Assertions.assertEquals(0, 0 / 4.0);
        Assertions.assertTrue(Double.isNaN(0 / 0.0));
    }

    @Test
    public void testDividingIntegerByZero() {
        assertThrows(ArithmeticException.class, () -> {
            int _ = 3 / 0;
        });
        assertEquals(0, 0 / 4);
        assertThrows(ArithmeticException.class, () -> {
            int _ = 0 / 0;
        });
    }

    @Test
    public void testBigDecimalDividing() {
        BigDecimal two = BigDecimal.TWO;
        BigDecimal three = BigDecimal.valueOf(3);
        Assertions.assertThrows(ArithmeticException.class, () -> two.setScale(2).divide(three));
        Assertions.assertEquals(new BigDecimal("0.67"), two.setScale(2).divide(three, RoundingMode.HALF_UP));
    }

    @Test
    public void testFloatingPointAccuracy() {
        Assertions.assertTrue(0.1 == 1.0 / 10.0);
        Assertions.assertEquals(0.1, 0.3 - 0.2, 1e-9);
    }

    @Test
    public void testMathApi() {
        Assertions.assertEquals(5, Math.abs(-5));
        Assertions.assertEquals(1, Math.min(1, 2));
        Assertions.assertEquals(2, Math.max(1, 2));
        Assertions.assertEquals(27, Math.pow(3, 3));
        Assertions.assertEquals(4, Math.sqrt(16));
        Assertions.assertEquals(Double.NaN, Math.sqrt(-1));
        Assertions.assertEquals(3.14, Math.round(Math.PI * 100) / 100.0);
    }
}
