package pl.mperor.lab.java.design.pattern.creational.prototype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;

public class SheepPrototypeTest {

    @Test
    public void testSheepCloning() {
        Sheep originalSheep = new Sheep("Dolly", new DNA("AGCT"), Year.of(2010), Sheep.Breed.MERINO);
        Sheep clonedSheep = originalSheep.clone(builder -> builder
                .name("Molly")
                .build()
        );
        Assertions.assertNotSame(originalSheep, clonedSheep);
        Assertions.assertSame(originalSheep.getDna(), clonedSheep.getDna());
        Assertions.assertSame(originalSheep.getBreed(), clonedSheep.getBreed());
        Assertions.assertNotEquals(originalSheep.getName(), clonedSheep.getName());
        Assertions.assertNotEquals(originalSheep.getYearOfBirth(), clonedSheep.getYearOfBirth());
    }
}