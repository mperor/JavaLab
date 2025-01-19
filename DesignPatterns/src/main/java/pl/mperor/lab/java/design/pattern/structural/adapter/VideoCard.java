package pl.mperor.lab.java.design.pattern.structural.adapter;

import java.util.function.Consumer;

public record VideoCard() {

    public void render(Consumer<DigitalSignal> consumer) {
        DigitalSignal renderedSignal = () -> System.out.println("Video card rendered signal 🎲");
        consumer.accept(renderedSignal);
    }
}
