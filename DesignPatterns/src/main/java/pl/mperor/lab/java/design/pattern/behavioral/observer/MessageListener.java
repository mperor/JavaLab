package pl.mperor.lab.java.design.pattern.behavioral.observer;

@FunctionalInterface
public interface MessageListener {

    void accept(String message);
}
