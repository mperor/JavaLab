package pl.mperor.lab.java.design.pattern.structural.decorator.classic;

public interface Coffee {

    int getCost();

    default String getDescription() {
        return this.getClass().getSimpleName();
    }
}
