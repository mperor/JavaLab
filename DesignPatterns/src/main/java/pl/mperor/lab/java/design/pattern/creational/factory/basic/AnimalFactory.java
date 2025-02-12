package pl.mperor.lab.java.design.pattern.creational.factory.basic;

interface AnimalFactory<T> {

    Animal getNewInstance(T type);
}
