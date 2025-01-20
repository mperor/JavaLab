package pl.mperor.lab.java.design.pattern.structural.bridge;

public record PetrolEngine() implements Engine {

    @Override
    public void start() {
        System.out.println("Petrol engine 🟢 has been started!");
    }
}
