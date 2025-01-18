package pl.mperor.lab.java.design.pattern.structural.composite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class Folder extends AddressablePath {

    private final AddressablePath[] paths;

    public Folder(String name, AddressablePath... paths) {
        super(name);
        this.paths = paths;
        for (AddressablePath path : paths) {
            path.setParent(this);
        }
    }

    public List<String> find(Predicate<String> nameMatcher) {
        List<String> result = new ArrayList<>();

        Arrays.stream(paths)
                .filter(path -> nameMatcher.test(path.getName()))
                .map(Object::toString)
                .forEach(result::add);

        Arrays.stream(paths)
                .filter(Folder.class::isInstance)
                .map(Folder.class::cast)
                .map(complexTag -> complexTag.find(nameMatcher))
                .flatMap(Collection::stream)
                .forEach(result::add);

        return result;
    }

}
