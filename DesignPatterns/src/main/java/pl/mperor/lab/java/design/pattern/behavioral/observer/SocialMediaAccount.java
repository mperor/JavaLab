package pl.mperor.lab.java.design.pattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class SocialMediaAccount implements MessagePublisher {

    private List<MessageListener> followers = new ArrayList<>();

    @Override
    public void follow(MessageListener follower) {
        followers.add(follower);
    }

    @Override
    public void unfollow(MessageListener follower) {
        followers.remove(follower);
    }

    @Override
    public void publish(String message) {
        followers.forEach(follower -> follower.accept(message));
    }
}
