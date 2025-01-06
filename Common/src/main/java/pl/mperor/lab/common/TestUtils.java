package pl.mperor.lab.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TestUtils {

    private static final PrintStream ORIGINAL = System.out;

    public static ReadableOut setTempSystemOut() {
        var temp = new ByteArrayOutputStream();
        System.setOut(new PrintStream(temp));
        return new ReadableOut(temp);
    }

    public static void resetSystemOut() {
        System.setOut(ORIGINAL);
    }

    public record ReadableOut(ByteArrayOutputStream out) {

        public String all() {
            return out.toString();
        }

        public ReadableList<String> lines() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(out.toByteArray())))) {
                return reader.lines().collect(Collectors.toCollection(ReadableArrayList::new));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class ReadableArrayList<E> extends ArrayList<E> implements ReadableList<E> {
    }

    public interface ReadableList<E> extends List<E> {
        default E getSecond() {
            return getNext(1);
        }

        default E getThird() {
            return getNext(2);
        }

        private E getNext(int index) {
            if (this.isEmpty() && index >= size()) {
                throw new NoSuchElementException();
            } else {
                return this.get(index);
            }
        }
    }

}
