package pl.mperor.lab.java.design.pattern.structural.decorator.classic;

public class NormalCoffee implements Coffee {

    @Override
    public int getCost() {
        return 100;
    }

    @Override
    public String getDescription() {
        return "Coffee";
    }
}
