package pl.mperor.lab.java.clean.code.ddd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

interface Event {

    record Publisher(Collection<EventConsumer> consumers) {

        public Publisher {
            consumers = List.copyOf(consumers);
        }

        Publisher onPublish(EventConsumer consumer) {
            var newConsumers = new ArrayList<>(consumers);
            newConsumers.add(consumer);
            return new Publisher(newConsumers);
        }

        void publish() {
            var event = new Event() {};
            consumers.forEach(consumer -> consumer.accept(event));
        }

        interface EventConsumer extends Consumer<Event> {
        }
    }
}
