package pl.mperor.lab.java.design.pattern.structural.composite;

import java.util.Arrays;
import java.util.List;

public record ComplexAbility(String name, List<Ability> abilities) implements Ability {

    public ComplexAbility(String name, Ability... abilities) {
        this(name, Arrays.asList(abilities));
    }

    @Override
    public int value() {
        return (int) abilities.stream()
                .mapToInt(Ability::value)
                .average()
                .orElse(0);
    }

}
