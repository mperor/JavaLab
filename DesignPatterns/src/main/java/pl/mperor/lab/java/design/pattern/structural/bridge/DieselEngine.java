package pl.mperor.lab.java.design.pattern.structural.bridge;

public record DieselEngine() implements Engine {

    @Override
    public void start() {
        System.out.println("Diesel engine 🟢 has been started!");
    }
}
