package pl.mperor.lab.java.design.pattern.creational.factory.basic;

record Dog() implements Animal {

    public static final String NAME = "Dog";

    @Override
    public String toString() {
        return NAME;
    }
}
