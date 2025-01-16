package pl.mperor.lab.java.design.pattern.structural.decorator.lambda.expression;

import java.util.Arrays;

@FunctionalInterface
public interface TextProcessor {

    String process(String text);

    default TextProcessor chain(TextProcessor after) {
        return input -> after.process(this.process(input));
    }

    static TextProcessor identity() {
        return text -> text;
    }

    static TextProcessor of(TextProcessor processor) {
        return processor;
    }

    static TextProcessor of(TextProcessor... processors) {
        return Arrays.stream(processors)
                .reduce(TextProcessor.identity(), TextProcessor::chain);
    }
}