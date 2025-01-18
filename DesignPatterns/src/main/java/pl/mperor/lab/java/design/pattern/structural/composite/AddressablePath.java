package pl.mperor.lab.java.design.pattern.structural.composite;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AddressablePath implements Nameable {

    private final String name;
    private Nameable parent;

    protected AddressablePath(String name) {
        this.name = name;
    }

    protected void setParent(Nameable parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (parent == null) {
            return name;
        }

        return Stream.of(parent.toString(), name)
                .collect(Collectors.joining("/"));
    }

}
