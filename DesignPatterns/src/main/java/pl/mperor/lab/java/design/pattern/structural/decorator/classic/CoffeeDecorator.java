package pl.mperor.lab.java.design.pattern.structural.decorator.classic;

public abstract class CoffeeDecorator implements Coffee {

    protected final Coffee coffee;
    protected final int cost;

    protected CoffeeDecorator(Coffee coffee, int cost) {
        this.coffee = coffee;
        this.cost = cost;
    }

    @Override
    public int getCost() {
        return coffee.getCost() + cost;
    }

    @Override
    public String getDescription() {
        return "%s + %s".formatted(coffee.getDescription(), Coffee.super.getDescription());
    }
}
