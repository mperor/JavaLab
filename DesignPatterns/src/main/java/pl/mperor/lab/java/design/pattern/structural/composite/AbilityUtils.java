package pl.mperor.lab.java.design.pattern.structural.composite;

import java.util.stream.Collectors;

public class AbilityUtils {

    private AbilityUtils() {
    }

    public static void printAbilityTree(Ability root) {
        System.out.println(printAbilityTree(root, 0));
    }

    private static String printAbilityTree(Ability ability, int level) {
        var nested = "";
        if (ability instanceof ComplexAbility ca) {
            nested = ca.abilities().stream()
                    .map(a -> printAbilityTree(a, level + 1))
                    .collect(Collectors.joining());
        }

        return "%s%s (%d)%n%s".formatted(" ".repeat(level), ability.name(), ability.value(), nested);
    }

}
