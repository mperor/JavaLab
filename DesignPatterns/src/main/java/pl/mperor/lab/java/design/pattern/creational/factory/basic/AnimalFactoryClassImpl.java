package pl.mperor.lab.java.design.pattern.creational.factory.basic;

public class AnimalFactoryClassImpl implements AnimalFactory<Class<? extends Animal>> {

    @Override
    public Animal getNewInstance(Class<? extends Animal> type) {
        return switch (type.getSimpleName()) {
            case Cat.NAME -> new Cat();
            case Dog.NAME -> new Dog();
            case Fly.NAME -> new Fly();
            default -> throw new IllegalStateException("Unexpected value: " + type.getSimpleName());
        };
    }
}
