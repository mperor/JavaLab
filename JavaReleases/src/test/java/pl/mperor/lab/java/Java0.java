package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Java 1.0 (January 1996)
 * <p>
 * - LANGUAGE FEATURES:
 * - Classes & interfaces (single inheritance, multiple interface implementation)
 * - Packages & access modifiers
 * - Exceptions (checked & unchecked)
 * - Threads & `synchronized` built into the language
 * - Garbage collection
 * - 16-bit Unicode `char`
 * <p>
 * - LIBRARIES & APIs:
 * - Legacy collections (`Vector`, `Hashtable`, `Stack`, `Enumeration`, `BitSet`)
 * - Byte streams (`InputStream` / `OutputStream`)
 * - Networking (`Socket`, `ServerSocket`, `URL`)
 * - Utilities (`Date`, `Random`, `Properties`, `StringTokenizer`, `Observable` / `Observer`)
 * - AWT (Abstract Window Toolkit)
 * - Applets (`java.applet`)
 * <p>
 * - TOOLS:
 * - `javac`, `java`, `javadoc`, `jdb`, `javap`
 * - `appletviewer`
 * - `javah` (native headers)
 */
public class Java0 {

    @Test
    public void testCovariantArrays() {
        // Arrays have been covariant since Java 1.0 - String[] is an Object[], so type errors surface only at runtime
        Object[] objects = new String[1];
        objects[0] = "text";
        Assertions.assertThrows(ArrayStoreException.class, () -> objects[0] = 1);
        // Generics (Java 5) are invariant instead - List<Object> list = new ArrayList<String>() does not compile
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void testRawTypes() {
        // Before generics (Java 5) collections held plain Objects - any type could be added and every read needed a cast
        Vector vector = new Vector();
        vector.addElement("a");
        vector.addElement(1);
        Assertions.assertEquals("a", (String) vector.elementAt(0));
        Assertions.assertThrows(ClassCastException.class, () -> {
            String text = (String) vector.elementAt(1); // type error surfaces only at runtime
        });
    }

    @Test
    public void testStringTokenizer() {
        // Before String.split (Java 1.4) StringTokenizer was the only built-in way to split text
        StringTokenizer tokenizer = new StringTokenizer("a,,b", ",");
        Assertions.assertEquals(2, tokenizer.countTokens()); // empty tokens are skipped
        Assertions.assertEquals("a", tokenizer.nextToken());
        Assertions.assertEquals("b", tokenizer.nextToken());
        Assertions.assertFalse(tokenizer.hasMoreTokens());

        Assertions.assertArrayEquals(new String[]{"a", "", "b"}, "a,,b".split(",")); // split keeps empty tokens
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDateQuirks() {
        // java.util.Date counts years from 1900 and months from 0 - Java 1.0 release date:
        Date releaseDate = new Date(96, 0, 23);
        Assertions.assertEquals(96, releaseDate.getYear());
        Assertions.assertEquals(0, releaseDate.getMonth());
        Assertions.assertEquals(23, releaseDate.getDate());

        // Date is mutable, so a shared instance can be changed by anyone holding a reference (java.time is immutable since Java 8)
        Date sharedDate = releaseDate;
        sharedDate.setDate(24);
        Assertions.assertEquals(24, releaseDate.getDate());
    }

    @Test
    public void testProperties() throws IOException {
        // Properties loads key=value text and falls back to defaults for missing keys
        Properties defaults = new Properties();
        defaults.setProperty("timeout", "30");
        Properties properties = new Properties(defaults);
        properties.load(new ByteArrayInputStream("""
                # comment
                host = localhost
                """.getBytes()));

        Assertions.assertEquals("localhost", properties.getProperty("host"));
        Assertions.assertEquals("30", properties.getProperty("timeout"));
        Assertions.assertNull(properties.get("timeout")); // Hashtable.get ignores defaults

        // Properties extends Hashtable<Object, Object> - a design flaw, as non-String values can be put but not read back
        // Since Java 9 the inherited Hashtable storage is unused - entries live in an internal ConcurrentHashMap (lock-free reads)
        Assertions.assertInstanceOf(Hashtable.class, properties);
        properties.put("port", 8080);
        Assertions.assertNull(properties.getProperty("port"));
    }

    @Test
    public void testRandomWithSeed() {
        // Random is a deterministic pseudo-random generator - the same seed always yields the same sequence
        Random first = new Random(42);
        Random second = new Random(42);
        for (int i = 0; i < 5; i++) {
            Assertions.assertEquals(first.nextInt(), second.nextInt());
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

    @Test
    public void testExceptionsAndFinally() {
        // Checked exceptions must be declared with `throws`, unchecked ones (RuntimeException subclasses) need not be
        Assertions.assertThrows(IOException.class, Java0::throwChecked);
        Assertions.assertThrows(IllegalStateException.class, Java0::throwUnchecked);

        // `return` in `finally` overrides the result of `try` and silently swallows any thrown exception
        Assertions.assertEquals("finally", returnFromTryAndFinally());
        Assertions.assertEquals("finally", throwFromTryAndReturnFromFinally());
    }

    private static void throwChecked() throws IOException {
        throw new IOException("must be declared");
    }

    private static void throwUnchecked() {
        throw new IllegalStateException("no declaration needed");
    }

    @SuppressWarnings("finally")
    private static String returnFromTryAndFinally() {
        try {
            return "try";
        } finally {
            return "finally";
        }
    }

    @SuppressWarnings("finally")
    private static String throwFromTryAndReturnFromFinally() {
        try {
            throw new IllegalStateException("lost");
        } finally {
            return "finally";
        }
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
    public void testLegacyCollections() {
        // Before the Collections Framework (Java 1.2) there were only Vector, Hashtable, Stack, Enumeration & BitSet
        assertVectorAndEnumeration();
        assertHashtable();
        assertStack();
        assertBitSet();
    }

    private void assertVectorAndEnumeration() {
        Vector<String> vector = new Vector<>();
        vector.addElement("a");
        vector.addElement("b");
        Assertions.assertEquals("a", vector.firstElement());
        Assertions.assertEquals("b", vector.elementAt(1));

        Enumeration<String> elements = vector.elements();
        Assertions.assertEquals("a", elements.nextElement());
        Assertions.assertEquals("b", elements.nextElement());
        Assertions.assertFalse(elements.hasMoreElements());
    }

    private void assertHashtable() {
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("one", 1);
        Assertions.assertEquals(1, hashtable.get("one"));
        Assertions.assertThrows(NullPointerException.class, () -> hashtable.put("two", null)); // null keys & values are not allowed
    }

    private void assertStack() {
        // Stack extends Vector - a design flaw, as it exposes index-based methods that break LIFO (Deque is preferred since Java 6)
        Stack<String> stack = new Stack<>();
        stack.push("bottom");
        stack.push("top");
        Assertions.assertEquals("top", stack.peek());
        Assertions.assertEquals(1, stack.search("top")); // 1-based distance from the top

        stack.insertElementAt("sneaky", 1); // inherited from Vector
        Assertions.assertEquals("top", stack.pop());
        Assertions.assertEquals("sneaky", stack.pop());
        Assertions.assertEquals("bottom", stack.pop());
        Assertions.assertTrue(stack.empty());
    }

    private void assertBitSet() {
        // BitSet is a growable vector of bits supporting logical operations
        BitSet evens = new BitSet();
        BitSet lowNumbers = new BitSet();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) evens.set(i);
            if (i < 5) lowNumbers.set(i);
        }
        evens.and(lowNumbers);
        Assertions.assertEquals("{0, 2, 4}", evens.toString());
        Assertions.assertFalse(evens.get(6));
    }

}
