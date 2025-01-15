package pl.mperor.lab.java.design.pattern.structural.decorator.lambda.expression;

import java.util.function.Predicate;

public class TextValidator {

    private TextValidator() {
    }

    public static Predicate<String> notEmpty() {
        return Predicate.not(String::isEmpty);
    }

    public static Predicate<String> lengthBetween(int min, int max) {
        return minLength(min).and(maxLength(max));
    }

    public static Predicate<String> minLength(int min) {
        return text -> text.length() >= min;
    }

    public static Predicate<String> maxLength(int max) {
        return text -> text.length() >= max;
    }

    public static Predicate<String> numeric() {
        return text -> text.matches("\\d+");
    }

    public static Predicate<String> nan() {
        return numeric().negate();
    }

}
