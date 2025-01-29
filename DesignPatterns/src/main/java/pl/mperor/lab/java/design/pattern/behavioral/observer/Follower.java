package pl.mperor.lab.java.design.pattern.behavioral.observer;

public record Follower(String name) implements MessageListener {

    @Override
    public void accept(String message) {
        System.out.printf("%s received message: %s%n", name, message);
    }
}