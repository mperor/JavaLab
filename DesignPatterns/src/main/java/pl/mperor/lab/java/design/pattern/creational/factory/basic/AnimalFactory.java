package pl.mperor.lab.java.design.pattern.creational.factory.basic;

public interface AnimalFactory<T> {

    Animal getNewInstance(T type);
}
