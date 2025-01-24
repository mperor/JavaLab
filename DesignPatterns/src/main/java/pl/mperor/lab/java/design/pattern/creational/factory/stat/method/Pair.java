package pl.mperor.lab.java.design.pattern.creational.factory.stat.method;

import java.util.Map;

record Pair<L, R>(L left, R right) {

    private static final Pair EMPTY = new Pair<>(null, null);

    static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    static <L, R> Pair<L, R> empty() {
        return EMPTY;
    }

    static <L, R> Pair<L, R> left(L left) {
        return of(left, null);
    }

    static <L, R> Pair<L, R> right(R right) {
        return of(null, right);
    }

    static <L, R> Pair<L, R> fromMapEntry(Map.Entry<L, R> mapEntry) {
        return of(mapEntry.getKey(), mapEntry.getValue());
    }
}
