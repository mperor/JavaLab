package pl.mperor.lab.java.design.pattern.creational.singleton;

public class LazyInitializedSingleton {

    private static LazyInitializedSingleton instance;
    private final long time = System.currentTimeMillis();

    private LazyInitializedSingleton() {
    }

    public static synchronized LazyInitializedSingleton getInstance() {
        if (instance == null) {
            instance = new LazyInitializedSingleton();
        }
        return instance;
    }

    public long getTime() {
        return time;
    }

}
