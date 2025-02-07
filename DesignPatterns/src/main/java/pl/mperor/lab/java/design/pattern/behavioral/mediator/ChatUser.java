package pl.mperor.lab.java.design.pattern.behavioral.mediator;

record ChatUser(ChatRoom mediator, String name) {

    void send(String message) {
        System.out.println(name + " sends: " + message);
        mediator.sendMessage(message, this);
    }

    void receive(String message) {
        System.out.println(name + " received: " + message);
    }
}