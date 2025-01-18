package pl.mperor.lab.java.design.pattern.structural.composite;

public interface Ability {

    String name();

    int value();

    static Ability of(String name, int value) {
        return new SimpleAbility(name, value);
    }

    static Ability of(String name, Ability... abilities) {
        return new ComplexAbility(name, abilities);
    }

}
