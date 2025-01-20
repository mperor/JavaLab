package pl.mperor.lab.java.design.pattern.structural.bridge;

public record Bus(Engine engine) implements Vehicle {

    @Override
    public void run() {
        engine.start();
        System.out.println("Bus 🚌 is running!");
    }
}
