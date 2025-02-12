package pl.mperor.lab.java.design.pattern.creational.singleton;

public class SingletonHolder implements SingletonInstance {

    private final long time = System.currentTimeMillis();

    private SingletonHolder() {
    }

    public static SingletonHolder getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long getCreationTime() {
        return time;
    }

    private static class Holder {
        private static final SingletonHolder INSTANCE = new SingletonHolder();
    }
}
