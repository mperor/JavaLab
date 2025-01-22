package pl.mperor.lab.java.design.pattern.creational.factory.basic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.design.pattern.creational.factory.basic.AnimalFactorySwitchExpressionImpl.Instances;

import static pl.mperor.lab.java.design.pattern.creational.factory.basic.AnimalFactoryEnumImpl.AnimalType;

public class AnimalFactoryTest {

    @Test
    public void animalFactoryMapImplShouldBeAbleToCreateAnimalsByNames() {
        AnimalFactory<String> factory = new AnimalFactoryMapImpl();
        Assertions.assertInstanceOf(Dog.class, factory.getNewInstance("Dog"));
        Assertions.assertInstanceOf(Fly.class, factory.getNewInstance("Fly"));
        Assertions.assertInstanceOf(Cat.class, factory.getNewInstance("Cat"));

        Assertions.assertNotSame(factory.getNewInstance("Dog"), factory.getNewInstance("Dog"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> factory.getNewInstance("Fish"));
    }

    @Test
    public void animalFactoryEnumImplShouldBeAbleToCreateAnimalsByEnum() {
        AnimalFactory<AnimalType> factory = new AnimalFactoryEnumImpl();
        Assertions.assertInstanceOf(Dog.class, factory.getNewInstance(AnimalType.DOG));
        Assertions.assertInstanceOf(Fly.class, factory.getNewInstance(AnimalType.FLY));
        Assertions.assertInstanceOf(Cat.class, factory.getNewInstance(AnimalType.CAT));

        Assertions.assertNotSame(factory.getNewInstance(AnimalType.DOG), factory.getNewInstance(AnimalType.DOG));
    }

    @Test
    public void animalFactorySwitchExpressionImplShouldBeAbleToCreateAnimalsByAnimalInstance() {
        AnimalFactory<Animal> factory = new AnimalFactorySwitchExpressionImpl();
        Assertions.assertInstanceOf(Dog.class, factory.getNewInstance(Instances.DOG));
        Assertions.assertInstanceOf(Fly.class, factory.getNewInstance(Instances.FLY));
        Assertions.assertInstanceOf(Cat.class, factory.getNewInstance(Instances.CAT));

        Assertions.assertNotSame(factory.getNewInstance(Instances.DOG), factory.getNewInstance(Instances.DOG));
    }

    @Test
    public void animalFactoryClassImplShouldBeAbleToCreateAnimalsByClass() {
        AnimalFactory<Class<? extends Animal>> factory = new AnimalFactoryClassImpl();
        Assertions.assertInstanceOf(Dog.class, factory.getNewInstance(Dog.class));
        Assertions.assertInstanceOf(Fly.class, factory.getNewInstance(Fly.class));
        Assertions.assertInstanceOf(Cat.class, factory.getNewInstance(Cat.class));

        Assertions.assertThrows(IllegalStateException.class, () -> factory.getNewInstance(Animal.class));
        Assertions.assertNotSame(factory.getNewInstance(Dog.class), factory.getNewInstance(Dog.class));
    }
}