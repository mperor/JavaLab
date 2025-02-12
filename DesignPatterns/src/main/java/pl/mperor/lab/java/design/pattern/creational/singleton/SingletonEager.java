package pl.mperor.lab.java.design.pattern.creational.singleton;

public class SingletonEager implements SingletonInstance {

    private static final SingletonEager INSTANCE = new SingletonEager();
    private final long time = System.currentTimeMillis();

    private SingletonEager() {
    }

    public static SingletonEager getInstance() {
        return INSTANCE;
    }

    @Override
    public long getCreationTime() {
        return time;
    }
}
