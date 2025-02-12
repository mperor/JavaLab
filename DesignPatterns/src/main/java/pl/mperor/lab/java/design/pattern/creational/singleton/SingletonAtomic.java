package pl.mperor.lab.java.design.pattern.creational.singleton;

import java.util.concurrent.atomic.AtomicReference;

public class SingletonAtomic implements SingletonInstance {

    private static final AtomicReference<SingletonAtomic> INSTANCE = new AtomicReference<>();
    private final long time = System.currentTimeMillis();

    private SingletonAtomic() {
    }

    public static SingletonAtomic getInstance() {
        INSTANCE.compareAndSet(null, new SingletonAtomic());
        return INSTANCE.get();
    }

    @Override
    public long getCreationTime() {
        return time;
    }
}