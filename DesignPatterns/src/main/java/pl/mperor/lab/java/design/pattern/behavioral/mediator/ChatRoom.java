package pl.mperor.lab.java.design.pattern.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class ChatRoom {

    private final List<ChatUser> users = new ArrayList<>();

    void addUser(ChatUser user) {
        users.add(user);
    }

    void sendMessage(String message, ChatUser sender) {
        users.stream()
                .filter(Predicate.not(sender::equals))
                .forEach(user -> user.receive(message));
    }
}
