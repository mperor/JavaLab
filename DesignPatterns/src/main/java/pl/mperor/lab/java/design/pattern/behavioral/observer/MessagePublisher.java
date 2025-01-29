package pl.mperor.lab.java.design.pattern.behavioral.observer;

public interface MessagePublisher {

    void follow(MessageListener participant);

    void unfollow(MessageListener participant);

    void publish(String message);
}
