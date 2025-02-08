package pl.mperor.lab.java.design.pattern.behavioral.interpreter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CommandInterpreterTest {

    @Test
    public void testCommandInterpreter() {
        Map<String, CommandInterpreter> commands = new HashMap<>();
        commands.put("/welcome", name -> "👋 Welcome " + name + "!");
        commands.put("/shout", message -> "❗" + message.toUpperCase());

        Assertions.assertEquals("👋 Welcome Alice 👱‍♀️!",
                commands.get("/welcome").interpret("Alice 👱‍♀️")
        );
        Assertions.assertEquals("❗ARE U READY?",
                commands.get("/shout").interpret("Are u ready?")
        );
    }
}