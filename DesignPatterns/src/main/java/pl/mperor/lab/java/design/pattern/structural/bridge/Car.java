package pl.mperor.lab.java.design.pattern.structural.bridge;

public record Car(Engine engine) implements Vehicle {

    @Override
    public void run() {
        engine.start();
        System.out.println("Car 🚗 is running!");
    }
}
