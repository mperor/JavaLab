package pl.mperor.lab.java.design.pattern.structural.decorator.classic;

public class WithSugar extends CoffeeDecorator {

    public WithSugar(Coffee coffee) {
        super(coffee, 30);
    }

}
