package pl.mperor.lab.java.design.pattern.creational.singleton;

public enum SingletonEnum implements SingletonInstance {
    INSTANCE;

    private final long time = System.currentTimeMillis();

    @Override
    public long getCreationTime() {
        return time;
    }
}
