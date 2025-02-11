package pl.mperor.lab.java.design.pattern.behavioral.strategy.classic;

import java.util.LinkedHashMap;
import java.util.Map;

class ShoppingCart {

    private final Map<Item, Integer> itemQuantities = new LinkedHashMap<>();

    void addItem(Item item) {
        addItem(item, 1);
    }

    void addItem(Item item, int quantities) {
        assert quantities >= 1;
        itemQuantities.merge(item, quantities, Integer::sum);
    }

    double computeTotal() {
        return itemQuantities.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().price() * entry.getValue())
                .sum();
    }
}
