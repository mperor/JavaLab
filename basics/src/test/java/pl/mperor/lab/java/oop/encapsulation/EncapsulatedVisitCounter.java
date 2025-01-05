package pl.mperor.lab.java.oop.encapsulation;

public class EncapsulatedVisitCounter implements Incrementable {
    private int visitCounter;

    @Override
    public void increment() {
        visitCounter++;
    }

    public int getVisitCounter() {
        return visitCounter;
    }
}
