package pl.mperor.lab.java.oop.encapsulation;

public class PublicVisitCounter implements Incrementable{
    public int visitCounter;

    @Override
    public void increment() {
        visitCounter++;
    }
}
