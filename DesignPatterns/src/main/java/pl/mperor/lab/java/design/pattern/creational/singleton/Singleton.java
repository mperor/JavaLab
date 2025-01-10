package pl.mperor.lab.java.design.pattern.creational.singleton;

public class Singleton {

    private static final Singleton instance = new Singleton();
    private final long time = System.currentTimeMillis();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }

    public long getTime() {
        return time;
    }

}
