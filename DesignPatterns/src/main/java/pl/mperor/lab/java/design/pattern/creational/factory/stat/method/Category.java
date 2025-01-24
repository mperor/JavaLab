package pl.mperor.lab.java.design.pattern.creational.factory.stat.method;

import java.util.HashMap;
import java.util.Map;

class Category {
    private static final Map<String, Category> CACHE = new HashMap<>();

    private final String name;

    private Category(String name) {
        this.name = name;
    }

    static Category of(String name) {
        return CACHE.computeIfAbsent(name.toLowerCase(), Category::new);
    }
}