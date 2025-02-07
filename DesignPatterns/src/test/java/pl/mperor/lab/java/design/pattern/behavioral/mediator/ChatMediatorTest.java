package pl.mperor.lab.java.design.pattern.behavioral.mediator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

public class ChatMediatorTest {

    @Test
    public void testChatRoomMediating() {
        var out = TestUtils.setTempSystemOut();

        var chat = new ChatRoom();
        var alice = new ChatUser(chat, "👩 Alice");
        var bob = new ChatUser(chat, "👨 Bob");

        chat.addUser(alice);
        chat.addUser(bob);

        alice.send("Hello everyone!");
        bob.send("Hi Alice!");

        var outLines = out.lines();
        Assertions.assertEquals("👩 Alice sends: Hello everyone!", outLines.getFirst());
        Assertions.assertEquals("👨 Bob received: Hello everyone!", outLines.getSecond());
        Assertions.assertEquals("👨 Bob sends: Hi Alice!", outLines.getThird());
        Assertions.assertEquals("👩 Alice received: Hi Alice!", outLines.getForth());

        TestUtils.resetSystemOut();
    }
}