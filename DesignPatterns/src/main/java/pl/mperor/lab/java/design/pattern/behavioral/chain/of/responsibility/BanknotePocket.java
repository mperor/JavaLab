package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

import java.util.EnumMap;
import java.util.Map;

class BanknotePocket {

    private final Map<Banknote, Integer> noteToAmount = new EnumMap<>(Banknote.class);

    void add(Banknote note, Integer amount) {
        noteToAmount.put(note, amount);
    }

    Integer get(Banknote note) {
        return noteToAmount.getOrDefault(note, 0);
    }
}
