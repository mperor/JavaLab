package pl.mperor.lab.java.design.pattern.structural.decorator.lambda.expression;

import java.util.Arrays;
import java.util.function.Function;

public class TextProcessor {

    TextProcessor() {
    }

    static Function<String, String> of(Function<String, String> processor) {
        return processor;
    }

    static Function<String, String> of(Function<String, String>... processors) {
        return Arrays.stream(processors)
                .reduce(Function.identity(), Function::andThen);
    }
}