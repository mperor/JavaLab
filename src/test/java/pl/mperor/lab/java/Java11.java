package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 11 (September 2018)
 */
public class Java11 {

    @Test
    public void testStringAPIEnhancements() {
        Assertions.assertTrue("  ".isBlank());
        Assertions.assertFalse("Java 11".isBlank());

        Assertions.assertEquals("***", "  ***  ".strip());
        Assertions.assertEquals("***  ", "  ***  ".stripLeading());
        Assertions.assertEquals("  ***", "  ***  ".stripTrailing());

        String multiline = """
                First
                Second
                Third""";
        Assertions.assertEquals(3, multiline.lines().count());

        Assertions.assertEquals("DDD", "D".repeat(3));
    }

    @Test
    public void testPredicateNot() {
        String nonBlankJoined = Stream.of("a", "", "b", "   ", "c")
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.joining());

        Assertions.assertEquals("abc", nonBlankJoined);
    }

    @Test
    public void testTransferTo() throws Exception {
        String data = "Java 11 InputStream transferTo method";

        try (ByteArrayInputStream input = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            input.transferTo(output);

            String result = output.toString(StandardCharsets.UTF_8);
            Assertions.assertEquals(data, result);
        }
    }

    @Test
    public void testTeeingCollector() {
        List<String> words = List.of("one", "two", "three");

        SimpleEntry<Integer, Long> pair = words.stream().collect(Collectors.teeing(
                Collectors.summingInt(String::length),
                Collectors.counting(),
                SimpleEntry::new)
        );

        Assertions.assertEquals(11, pair.getKey());
        Assertions.assertEquals(3, pair.getValue());
    }

    @Test
    public void testCollectionToArray() {
        Assertions.assertArrayEquals(new String[]{"x", "y", "z"}, List.of("x", "y", "z").toArray(String[]::new));
    }

}