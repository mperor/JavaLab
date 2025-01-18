package pl.mperor.lab.java.design.pattern.structural.composite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AbilityTreeCompositeTest {

    @Test
    public void testCreateAbilityTree() {
        var physicalHealth = Ability.of("Physical Health",
                Ability.of("Fitness",
                        Ability.of("Jogging", 20),
                        Ability.of("Stretching", 40),
                        Ability.of("Swimming", 60)
                ),
                Ability.of("Sleep", 50),
                Ability.of("Nutrition", 30)
        );
        Assertions.assertEquals(40, physicalHealth.value());

        var mentalHealth = Ability.of("Mental Health",
                Ability.of("Stress Management", 50)
        );
        Assertions.assertEquals(50, mentalHealth.value());

        var lifeAndHealth = Ability.of("Life and Health",
                physicalHealth,
                mentalHealth,
                Ability.of("Social Skills", 30)
        );
        Assertions.assertEquals(40, lifeAndHealth.value());

        AbilityUtils.printAbilityTree(lifeAndHealth);
    }

}