package pl.mperor.lab.java.design.pattern.behavioral.strategy.lightweight;

import java.util.List;
import java.util.function.Predicate;

class MathUtils {

    static int totalValue(List<Integer> values) {
        return totalValue(values, _ -> true);
    }

    static int totalValue(List<Integer> values, Predicate<Integer> selector) {
        return values.stream()
                .filter(selector)
                .mapToInt(e -> e)
                .sum();
    }

    static boolean isEven(int number) {
        return number % 2 == 0;
    }

    static boolean isOdd(int number) {
        return !isEven(number);
    }
}
