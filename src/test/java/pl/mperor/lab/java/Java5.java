package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;

/**
 * Java 1.5 (September 2004)
 */
public class Java5 {

    @Test
    public void testGenerics() {
        Box<String> genericBox = new Box<>();
        genericBox.setContent("Hello");
        String genericBoxContent = genericBox.getContent();
        Assertions.assertEquals("Hello", genericBoxContent);

        Box rawBox = new Box();
        rawBox.setContent("Hello").setContent(1);
        Assertions.assertThrows(ClassCastException.class, () -> {
            String rawBoxContent = (String) rawBox.getContent();
        });
        Integer rawBoxContent = (Integer) rawBox.getContent();
        Assertions.assertEquals(1, rawBoxContent);
    }

    class Box<T> {
        private T content;

        public Box<T> setContent(T content) {
            this.content = content;
            return this;
        }

        public T getContent() {
            return content;
        }
    }

    @Test
    public void testEnhancedForLoopAkaForEachLoop() {
        List<String> strings = Arrays.asList("one", "two", "three");
        int i = 0;
        for (String string : strings) {
            Assertions.assertEquals(string, strings.get(i++));
        }
    }

    @Test
    public void testUnboxingAndAutoboxing() {
        Integer x = 10;  // Autoboxing: converting 'int' to 'Integer'
        int y = x;       // Unboxing: converting 'Integer' to 'int'
        Assertions.assertSame(x, y);
    }

    @Test
    public void testVariableArgumentsAkaVarargs() {
        Assertions.assertEquals("123", concat("1", "2", "3"));
    }

    public String concat(String... names) {
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name);
        }
        return sb.toString();
    }

    @Test
    public void testEnumAkaEnumeration() {
        WeekDay firstDayOfWeek = WeekDay.MONDAY;
        Assertions.assertEquals(0, firstDayOfWeek.ordinal());
        Assertions.assertEquals("MONDAY", firstDayOfWeek.name());
        Assertions.assertEquals(7, WeekDay.values().length);
    }

    enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    @Test
    @MyRuntimeAnnotation("my value")
    public void testAnnotation() throws NoSuchMethodException {
        Method method = this.getClass().getMethod("testAnnotation");

        Class<? extends Annotation>[] builtInAnnotations = new Class[]{Override.class, SuppressWarnings.class, Deprecated.class};
        for (Class<? extends Annotation> builtInAnnotation : builtInAnnotations) {
            Assertions.assertFalse(method.isAnnotationPresent(builtInAnnotation));
        }

        Assertions.assertTrue(method.isAnnotationPresent(MyRuntimeAnnotation.class));
        Assertions.assertEquals("my value", method.getAnnotation(MyRuntimeAnnotation.class).value());
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyRuntimeAnnotation {
        String value();
    }

    @Test
    public void testConcurrencyAPI() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        var original = System.out;
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        IntStream.rangeClosed(1, 3).forEach(i ->
                executor.submit(() -> {
                    // Code executed in a separate thread
                    System.out.printf("Task %d ", i);
                })
        );
        executor.close();
        Assertions.assertEquals("Task 1 Task 2 Task 3 ", out.toString());
        System.setOut(original);
    }

    @Test
    public void testStaticImports() {
        Assertions.assertEquals(4, sqrt(16));   // Math.sqrt(16);
        Assertions.assertEquals(1, max(-1, 1)); // Math.max(-1, 1);
    }

    @Test
    public void testStringBuilderInsteadOfStringBuffer() {
        //almost the same as StringBuffer (Thread-Safe but Slower)
        StringBuilder sb = new StringBuilder();
        sb.append("dlroW ").append("olleH");
        Assertions.assertEquals("Hello World", sb.reverse().toString());
    }

}
