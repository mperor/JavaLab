package pl.mperor.lab.java.design.pattern.structural.decorator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextProcessorTest {

    @Test
    public void shouldAllowToCreateTextProcessorAsChainOfFunctions() {
        Assertions.assertEquals("prefix@Hello World@suffix",
                TextProcessor.of(String::strip)
                        .chain(s -> "prefix" + s)
                        .chain(s -> s + "suffix")
                        .apply("  @Hello World@")
        );

        Assertions.assertEquals("HEX",
                TextProcessor.of(
                        String::toUpperCase,
                        s -> s.replace("L", "X"),
                        s -> s.substring(0, 3)
                ).apply("Hello World")
        );
    }

}
