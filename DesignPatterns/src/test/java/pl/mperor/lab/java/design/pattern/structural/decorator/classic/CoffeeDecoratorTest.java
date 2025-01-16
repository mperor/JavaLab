package pl.mperor.lab.java.design.pattern.structural.decorator.classic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class CoffeeDecoratorTest {

    @Test
    public void shouldAllowToDynamicallyExtendCoffeeWithMilkChocolateOrSugar() {
        NormalCoffee normal = new NormalCoffee();
        Coffee robustCoffee = new WithChocolate(new WithMilk(new WithSugar(normal)));
        Assertions.assertTrue(normal.getCost() < robustCoffee.getCost());
        Assertions.assertTrue(Stream.of("WithChocolate", "WithSugar", "WithMilk")
                .allMatch(robustCoffee.getDescription()::contains)
        );
    }

}