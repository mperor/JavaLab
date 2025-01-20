package pl.mperor.lab.java.design.pattern.structural.bridge;

public sealed interface Vehicle permits Bus, Car {

    void run();
}
