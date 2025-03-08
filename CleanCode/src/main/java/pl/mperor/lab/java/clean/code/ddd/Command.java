package pl.mperor.lab.java.clean.code.ddd;

interface Command {

    interface CommandHandler<C extends Command> {
        Status handle(C command);
    }

    enum Status {
        DONE
    }
}
