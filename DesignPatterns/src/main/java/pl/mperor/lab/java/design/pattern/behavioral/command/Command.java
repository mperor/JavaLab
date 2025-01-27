package pl.mperor.lab.java.design.pattern.behavioral.command;

@FunctionalInterface
public interface Command {

    void execute();

    default Command reversed() {
        return this;
    }
}
