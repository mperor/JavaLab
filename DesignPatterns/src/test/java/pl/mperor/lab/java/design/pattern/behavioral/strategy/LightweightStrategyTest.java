package pl.mperor.lab.java.design.pattern.behavioral.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

public class LightweightStrategyTest {

    @Test
    public void testTotalValueStrategy() {
        var values = List.of(1, 2, 3, 4);
        Assertions.assertEquals(10, totalValue(values));
        Assertions.assertEquals(6, totalValue(values, LightweightStrategyTest::isEven));
        Assertions.assertEquals(4, totalValue(values, LightweightStrategyTest::isOdd));
    }

    public static int totalValue(List<Integer> values) {
        return totalValue(values, _ -> true);
    }

    public static int totalValue(List<Integer> values, Predicate<Integer> selector) {
        return values.stream()
                .filter(selector)
                .mapToInt(e -> e)
                .sum();
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isOdd(int number) {
        return !isEven(number);
    }
}
