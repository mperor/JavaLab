package pl.mperor.lab.java.clean.code.ddd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

/**
 * ⚡ Command vs Event 🎯
 *
 * <p><b>Command:</b> An instruction to perform an action, usually leading to a state change.
 * - Imperative 🏗️
 * - Synchronous or Asynchronous ⏳
 * - Expect a specific effect 🔄
 *
 * <p><b>Event:</b> A fact that something has happened in the past.
 * - Declarative 📢
 * - Asynchronous-friendly ⏩
 * - Captures history 🕰️
 */
class CommandVsEventTest {

    private final Command.CommandHandler handler = command -> {
        System.out.printf("🫵 Command: %s%n", command);
        return Command.Status.DONE;
    };

    private final Event.Publisher publisher = new Event.Publisher(Collections.emptyList());

    @Test
    void commandShouldExecuteImmediately() {
        Assertions.assertEquals(Command.Status.DONE, handler.handle(new Command() {}));
    }

    @Test
    void commandsCanBeHandledByTheSameHandler() {
        record CreateOrderCommand(UUID uuid) implements Command {}
        record CreatePaymentCommand(UUID uuid) implements Command {}
        Assertions.assertEquals(Command.Status.DONE, handler.handle(new CreateOrderCommand(UUID.randomUUID())));
        Assertions.assertEquals(Command.Status.DONE, handler.handle(new CreatePaymentCommand(UUID.randomUUID())));
    }

    @Test
    void eventShouldNotBeConsumeWithoutPublishing() {
        publisher.onPublish(_ -> Assertions.fail());
    }

    @Test
    void eventShouldBeConsumeByManyConsumers() {
        publisher.onPublish(event -> System.out.printf("❗Event: %s%n", event))
                .onPublish(Assertions::assertNotNull)
                .publish();
    }
}
