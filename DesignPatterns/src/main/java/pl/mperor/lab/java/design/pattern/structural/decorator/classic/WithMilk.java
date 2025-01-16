package pl.mperor.lab.java.design.pattern.structural.decorator.classic;

public class WithMilk extends CoffeeDecorator {

    public WithMilk(Coffee coffee) {
        super(coffee, 50);
    }

}
