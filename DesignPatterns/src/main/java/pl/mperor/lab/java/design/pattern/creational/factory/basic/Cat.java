package pl.mperor.lab.java.design.pattern.creational.factory.basic;

record Cat() implements Animal {

    public static final String NAME = "Cat";

    @Override
    public String toString() {
        return NAME;
    }
}
