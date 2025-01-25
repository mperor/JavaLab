package pl.mperor.lab.java.design.pattern.behavioral.strategy;

import java.util.List;
import java.util.function.Predicate;

public class MathUtils {

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
