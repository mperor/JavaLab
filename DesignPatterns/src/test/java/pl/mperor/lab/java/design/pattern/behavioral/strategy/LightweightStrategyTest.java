package pl.mperor.lab.java.design.pattern.behavioral.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LightweightStrategyTest {

    @Test
    public void testTotalValueStrategy() {
        var values = List.of(1, 2, 3, 4);
        Assertions.assertEquals(10, MathUtils.totalValue(values));
        Assertions.assertEquals(6, MathUtils.totalValue(values, MathUtils::isEven));
        Assertions.assertEquals(4, MathUtils.totalValue(values, MathUtils::isOdd));
    }
}
