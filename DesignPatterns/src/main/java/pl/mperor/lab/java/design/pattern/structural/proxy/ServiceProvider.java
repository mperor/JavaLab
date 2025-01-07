package pl.mperor.lab.java.design.pattern.structural.proxy;

import java.util.function.Supplier;

public enum ServiceProvider implements Supplier<ExecutableService> {
    INSTANCE;

    @Override
    public ExecutableService get() {
        return new ServiceProxy();
    }
}
