package pl.mperor.lab.java.design.pattern.creational.factory.basic;

class AnimalFactorySwitchExpressionImpl implements AnimalFactory<Animal> {

    public static class Instances {
        public static final Animal CAT = new Cat();
        public static final Animal DOG = new Dog();
        public static final Animal FLY = new Fly();
    }

    @Override
    public Animal getNewInstance(Animal type) {
        return switch (type) {
            case Cat _ -> new Cat();
            case Dog _ -> new Dog();
            case Fly _ -> new Fly();
        };
    }
}
