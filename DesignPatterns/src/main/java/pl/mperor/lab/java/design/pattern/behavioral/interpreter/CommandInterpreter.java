package pl.mperor.lab.java.design.pattern.behavioral.interpreter;

@FunctionalInterface
interface CommandInterpreter {

    String interpret(String source);
}
