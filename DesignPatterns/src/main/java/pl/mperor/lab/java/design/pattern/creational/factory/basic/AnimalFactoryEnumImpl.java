package pl.mperor.lab.java.design.pattern.creational.factory.basic;

import java.util.function.Supplier;

import static pl.mperor.lab.java.design.pattern.creational.factory.basic.AnimalFactoryEnumImpl.AnimalType;

public class AnimalFactoryEnumImpl implements AnimalFactory<AnimalType> {

    @Override
    public Animal getNewInstance(AnimalType type) {
        return type.getCreator().get();
    }

    public enum AnimalType {
        DOG(() -> new Dog()),
        CAT(() -> new Cat()),
        FLY(() -> new Fly());

        private final Supplier<Animal> creator;

        AnimalType(Supplier<Animal> creator) {
            this.creator = creator;
        }

        public Supplier<Animal> getCreator() {
            return creator;
        }
    }

}
