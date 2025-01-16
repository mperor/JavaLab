package pl.mperor.lab.java.design.pattern.structural.decorator.lambda.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextProcessorAndValidatorTest {

    @Test
    public void shouldAllowToCreateTextProcessorAsChainOfFunctions() {
        Assertions.assertEquals("prefix@Hello World@suffix",
                TextProcessor.of(String::strip)
                        .chain(s -> "prefix" + s)
                        .chain(s -> s + "suffix")
                        .process("  @Hello World@")
        );

        Assertions.assertEquals("HEX",
                TextProcessor.of(
                        String::toUpperCase,
                        s -> s.replace("L", "X"),
                        s -> s.substring(0, 3)
                ).process("Hello World")
        );
    }

    @Test
    public void shouldAllowToCreateTextValidator() {
        Assertions.assertTrue(
                TextValidator.notEmpty()
                        .and(TextValidator.nan())
                        .and(TextValidator.lengthBetween(1, 3))
                        .test("abc")
        );

        var blankOrNumeric = TextValidator.numeric().or(String::isBlank);
        Assertions.assertTrue(blankOrNumeric.test(" ") && blankOrNumeric.test("123"));
    }

}
