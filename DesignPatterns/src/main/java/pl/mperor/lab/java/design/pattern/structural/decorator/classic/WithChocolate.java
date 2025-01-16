package pl.mperor.lab.java.design.pattern.structural.decorator.classic;

public class WithChocolate extends CoffeeDecorator {

    public WithChocolate(Coffee coffee) {
        super(coffee, 150);
    }

}
