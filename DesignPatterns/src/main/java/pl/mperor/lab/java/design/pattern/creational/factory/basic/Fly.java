package pl.mperor.lab.java.design.pattern.creational.factory.basic;

record Fly() implements Animal {

    public static final String NAME = "Fly";

    @Override
    public String toString() {
        return NAME;
    }
}
