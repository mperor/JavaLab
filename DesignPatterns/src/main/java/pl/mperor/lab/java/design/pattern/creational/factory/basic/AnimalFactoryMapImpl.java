package pl.mperor.lab.java.design.pattern.creational.factory.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AnimalFactoryMapImpl implements AnimalFactory<String> {

    private final Map<String, Supplier<Animal>> nameToCreator = new HashMap<>();

    public AnimalFactoryMapImpl() {
        nameToCreator.put(Fly.NAME, Fly::new);
        nameToCreator.put(Dog.NAME, Dog::new);
        nameToCreator.put(Cat.NAME, Cat::new);
    }

    public Animal getNewInstance(String type) {
        if (nameToCreator.containsKey(type)) {
            return nameToCreator.get(type).get();
        }
        throw new IllegalArgumentException("There is no animal for this name " + type);
    }
}
