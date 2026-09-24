package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Java 1.0 (January 1996)
 *
 * - LANGUAGE FEATURES:
 *     - Classes & interfaces (single inheritance, multiple interface implementation)
 *     - Packages & access modifiers
 *     - Exceptions (checked & unchecked)
 *     - Threads & `synchronized` built into the language
 *     - Garbage collection
 *     - 16-bit Unicode `char`
 *
 * - LIBRARIES & APIs:
 *     - Legacy collections (`Vector`, `Hashtable`, `Stack`, `Enumeration`, `BitSet`)
 *     - Byte streams (`InputStream` / `OutputStream`)
 *     - Networking (`Socket`, `ServerSocket`, `URL`)
 *     - Utilities (`Date`, `Random`, `Properties`, `StringTokenizer`, `Observable` / `Observer`)
 *     - AWT (Abstract Window Toolkit)
 *     - Applets (`java.applet`)
 *
 * - TOOLS:
 *     - `javac`, `java`, `javadoc`, `jdb`, `javap`
 *     - `appletviewer`
 *     - `javah` (native headers)
 */
public class Java0 {

    @Test
    public void testLegacyCollections() {
        // Before the Collections Framework (Java 1.2) there were only Vector, Hashtable & Enumeration (all synchronized)
        Vector<String> vector = new Vector<>();
        vector.addElement("a");
        vector.addElement("b");
        Assertions.assertEquals("a", vector.firstElement());
        Assertions.assertEquals("b", vector.elementAt(1));

        Enumeration<String> elements = vector.elements();
        Assertions.assertEquals("a", elements.nextElement());
        Assertions.assertEquals("b", elements.nextElement());
        Assertions.assertFalse(elements.hasMoreElements());

        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("one", 1);
        Assertions.assertEquals(1, hashtable.get("one"));
        Assertions.assertThrows(NullPointerException.class, () -> hashtable.put("two", null)); // null keys & values are not allowed
    }

    @Test
    @Timeout(5)
    public void testWaitAndNotify() throws InterruptedException {
        // Before java.util.concurrent (Java 5) threads were coordinated only with synchronized, wait() & notify()
        var mailbox = new Mailbox();
        Thread producer = new Thread(() -> mailbox.put("Hello"));
        producer.start();

        Assertions.assertEquals("Hello", mailbox.take()); // blocks until the producer puts a message
        producer.join();
    }

    static class Mailbox {
        private String message;

        synchronized void put(String message) {
            this.message = message;
            notify();
        }

        synchronized String take() throws InterruptedException {
            while (message == null) { // loop guards against spurious wake-ups
                wait();
            }
            return message;
        }
    }

    @Test
    public void testUnicodeSupport() {
        // Since Java 1.0 `char` is a 16-bit Unicode code unit, covering the Basic Multilingual Plane (BMP)
        char englishLetter = '\u0041';      // Latin capital letter A (A)
        char polishLetter = '\u0104';       // Latin capital letter A with ogonek (Ą)
        char greekLetter = '\u0391';        // Greek capital letter Alpha (Α)
        char chineseCharacter = '\u4E2D';   // Chinese character for "middle" (中)

        Assertions.assertEquals('A', englishLetter);
        Assertions.assertEquals('Ą', polishLetter);
        Assertions.assertEquals('Α', greekLetter);
        Assertions.assertEquals('中', chineseCharacter);
        Assertions.assertNotEquals(englishLetter, greekLetter); // look alike, but different characters
    }

}
