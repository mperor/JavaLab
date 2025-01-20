package pl.mperor.lab.java.design.pattern.structural.bridge;

public sealed interface Engine permits DieselEngine, PetrolEngine {

    void start();
}
