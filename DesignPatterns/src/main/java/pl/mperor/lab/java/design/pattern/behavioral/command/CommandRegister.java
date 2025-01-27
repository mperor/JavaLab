package pl.mperor.lab.java.design.pattern.behavioral.command;

import java.util.Deque;
import java.util.LinkedList;

class CommandRegister {

    private final Deque<Command> historyCommands = new LinkedList<>();
    private final Deque<Command> redoCommands = new LinkedList<>();

    void execute(Command command) {
        command.execute();
        historyCommands.push(command);
        redoCommands.clear();
    }

    void undo() {
        if (!historyCommands.isEmpty()) {
            var command = historyCommands.pop();
            redoCommands.push(command);
            command.reversed().execute();
        }
    }

    void redo() {
        if (!redoCommands.isEmpty()) {
            var command = redoCommands.pop();
            historyCommands.push(command);
            command.execute();
        }
    }
}
