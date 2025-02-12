package pl.mperor.lab.java.design.pattern.creational.singleton;

import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;

public class SingletonSerializable implements Serializable, SingletonInstance {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final SingletonSerializable INSTANCE = new SingletonSerializable();
    private final long time = System.currentTimeMillis();

    private SingletonSerializable() {
    }

    public static SingletonSerializable getInstance() {
        return INSTANCE;
    }

    @Override
    public long getCreationTime() {
        return time;
    }

    // Prevent Serialization from creating a new instance
    protected Object readResolve() throws ObjectStreamException {
        return getInstance();
    }
}
