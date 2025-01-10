package pl.mperor.lab.java.design.pattern.behavioral.iterator;

import org.junit.jupiter.api.Test;

import java.util.List;

public class IteratorTest {

    private final List<String> names = List.of("Adam", "Bob", "Conor");

    @Test
    public void testIteratorImperatively() {
        findName("Bob");
    }

    public boolean findName(String n) {
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(n)) {
                return true;
            }
        }

        // same as foreach
        for (String name : names) {
            if (name.equals(n)) {
                return true;
            }
        }

        return false;
    }

    @Test
    public void testIteratorFunctionally() {
        // internal iterator

    }

}
