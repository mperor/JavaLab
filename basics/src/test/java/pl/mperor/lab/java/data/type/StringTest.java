package pl.mperor.lab.java.data.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

public class StringTest {

    @Test
    public void testStringCommonlyUsedMethods() {
        String s = "abc";
        Assertions.assertEquals(3, s.length());
        Assertions.assertTrue(s.contains("b"));
        Assertions.assertTrue(!s.isBlank() && !s.isEmpty());
        Assertions.assertTrue(s.startsWith("a") && s.endsWith("c"));
        Assertions.assertEquals("a*c", s.replace('b', '*'));
        Assertions.assertEquals("bc", s.substring(1, s.length()));
        Assertions.assertArrayEquals(new String[]{"google", "com"}, "google.com".split("\\."));
        Assertions.assertTrue("-1".matches("^-?\\d+$"), "Like isInteger(...)");
    }

    @Test
    public void testStringImmutability() {
        String original = "| Fix";
        String modified = original
                .replace('|', ' ')
                .strip()
                .concat("ed")
                .toUpperCase();

        Assertions.assertEquals("| Fix", original);
        Assertions.assertEquals("FIXED", modified);
    }

    @Test
    public void testCompareStrings() {
        String qwerty = "qwerty";
        String newQwerty = new String(qwerty);
        Assertions.assertTrue("qwerty" == qwerty, "String Pool exits!");
        Assertions.assertFalse(qwerty == newQwerty);
        Assertions.assertTrue(qwerty == newQwerty.intern(), "'intern()' returns string from the pool");
        Assertions.assertTrue(qwerty.equals(newQwerty));
        Assertions.assertTrue(qwerty.equalsIgnoreCase("Qwerty"));
    }

    @Test
    public void testEscapeSequences() {
        String doubleQuote = "\"Title\"";
        String tabulator = "\tabc";
        String backslash = "C:\\Windows";
        String newLine = "...\n***";
        String carriageReturn = "[          ] 0%\r[==========] 100%";
        String unicodeCode = "Copyright \u00A9";
        List.of(doubleQuote, tabulator, backslash, newLine, carriageReturn, unicodeCode)
                .forEach(System.out::println);
    }

    @Test
    public void testStringFormatting() {
        // Format specifier: %[flags][width][.precision]conversion
        // Escaping %%
        String format = "%s | %d | %#x | %.2f | %b | %c | %%%n";
        String formatted = String.format(Locale.UK, format, "Str", 1, 10, 1.23, true, '.');
        Assertions.assertEquals("Str | 1 | 0xa | 1.23 | true | . | %\r\n", formatted);
    }

}
