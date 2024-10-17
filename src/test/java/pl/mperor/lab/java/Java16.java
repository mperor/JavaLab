package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import pl.mperor.lab.TestUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Java 16 (March 2021)
 */
public class Java16 {

    @Test
    public void testPeriodSupport() {
        LocalTime date = LocalTime.parse("16:00:00");
        // symbol "B" alternative to the am/pm format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h B", Locale.US);
        Assertions.assertEquals("4 in the afternoon", date.format(formatter));
    }

    @Test
    public void testStreamToList() {
        var source = List.of(1, 2, 3);
        var toList = source.stream().toList();
        var collectToList = source.stream().collect(Collectors.toList());
        Assertions.assertEquals(toList, collectToList);
    }

    @Test
    public void testLocalElements() {
        var out = TestUtils.setTempSystemOut();

        interface LocalInterface {
            default void call() {
                System.out.println("Local interface work!");
            }
        }

        record LocalRecord() implements LocalInterface {
            @Override
            public void call() {
                System.out.println("Local records work!");
            }
        }

        enum LocalEnum implements LocalInterface {
            SINGLE;

            @Override
            public void call() {
                System.out.println("Local enums work!");
            }
        }

        new LocalRecord().call();
        LocalEnum.SINGLE.call();
        new LocalInterface() {}.call();

        var lines = out.lines();
        Assertions.assertEquals("Local records work!", lines.getFirst());
        Assertions.assertEquals("Local enums work!", lines.getSecond());
        Assertions.assertEquals("Local interface work!", lines.getThird());
        TestUtils.resetSystemOut();
    }

    @SuppressWarnings({"removal", "synchronization"})
    @Test
    public void testValueBasedClasses() {
        // warning: new Long(...) has been deprecated and marked for removal
        Long longByConstructor = new Long(13);

        // warning: attempt to synchronize on an instance of a value-based class
        synchronized (longByConstructor) {
            System.out.println("From the synchronized block!");
        }

        Long longByFactoryMethod = Long.valueOf(13L);
        Assertions.assertFalse(longByConstructor == longByFactoryMethod);
        Assertions.assertTrue(longByConstructor.equals(longByFactoryMethod));
        Assertions.assertTrue(Long.valueOf(13L) == longByFactoryMethod);
    }

    @Disabled("Dependent on the OS and additional libraries, besides having a long execution time.")
    @Test
    public void testJPackage() throws IOException, InterruptedException {
        Process process = new ProcessBuilder(
                "jpackage",
                "--name", "j16pack",
                "--input", "./build/libs",
                "--main-jar", "JavaLab-1.0-SNAPSHOT.jar",
                "--main-class", "pl.mperor.lab.java.Main"
        ).start();

        Assertions.assertEquals(0, process.waitFor());
        process.destroy();
    }

}