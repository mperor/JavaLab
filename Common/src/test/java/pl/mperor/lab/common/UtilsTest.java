package pl.mperor.lab.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

class UtilsTest {

    @Test
    public void testReadableOutputReturnsReadableList() {
        var out = TestUtils.setTempSystemOut();
        Stream.of("One", "Two", "Three")
                .forEach(System.out::println);
        var readableList = out.lines();
        Assertions.assertEquals("One", readableList.getFirst());
        Assertions.assertEquals("Two", readableList.getSecond());
        Assertions.assertEquals("Three", readableList.getThird());
        TestUtils.resetSystemOut();
    }

    @Test
    public void testNoOutPrintsReturnsEmptyReadableList() {
        var out = TestUtils.setTempSystemOut();
        var readableList = out.lines();
        Assertions.assertThrows(NoSuchElementException.class, () -> readableList.getFirst());
        Assertions.assertThrows(NoSuchElementException.class, () -> readableList.getSecond());
        Assertions.assertThrows(NoSuchElementException.class, () -> readableList.getThird());
        Assertions.assertEquals("", out.all());
        TestUtils.resetSystemOut();
    }

}