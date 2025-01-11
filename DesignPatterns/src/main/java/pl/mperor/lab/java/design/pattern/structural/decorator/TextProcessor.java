package pl.mperor.lab.java.design.pattern.structural.decorator;

import java.util.Arrays;
import java.util.function.UnaryOperator;

@FunctionalInterface
public interface TextProcessor extends UnaryOperator<String> {

    default TextProcessor chain(TextProcessor after) {
        return input -> after.apply(this.apply(input));
    }

    static TextProcessor identity() {
        return s -> s;
    }

    static TextProcessor of(TextProcessor processor) {
        return processor;
    }

    static TextProcessor of(TextProcessor... processors) {
        return Arrays.stream(processors)
                .reduce(TextProcessor.identity(), TextProcessor::chain);
    }
}