package pl.mperor.lab.java.lang.element;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MethodTest {

    @Test
    public void testOverloading() {
        byte b = 1;
        Assertions.assertEquals(1, Overloading.countArgs(b), "Type Promotion");
        Assertions.assertEquals(1, Overloading.countArgs(1.0), "Different Parameter Types");
        Assertions.assertEquals(2, Overloading.countArgs(1, 'a'), "Different Number of Parameters");
        Assertions.assertEquals(3, Overloading.countArgs("one", "two", "three"), "Varargs");
        Assertions.assertEquals(0, Overloading.countArgs(), "Varargs");
        Assertions.assertEquals(1, new Overloading().countArgs('c'), "Not only static!");
    }

    static class Overloading {
        static int countArgs(long l) {
            return 1;
        }

        static int countArgs(double d) {
            return 1;
        }

        static <T> int countArgs(long l, T t) {
            return 2;
        }

        static <T> int countArgs(T... t) {
            return t != null ? t.length : 0;
        }

        int countArgs(char c) {
            return 1;
        }
    }

    @Test
    public void testParameterPassingMechanism() {
        int primitive = 0;
        ParameterPassing.primitiveByValue(primitive);
        Assertions.assertEquals(0, primitive);

        String string = "original";
        ParameterPassing.immutableObjectByValue(string);
        Assertions.assertEquals("original", string);

        int[] array = {1, 2, 3};
        ParameterPassing.mutableObjectByValue(array);
        Assertions.assertArrayEquals(new int[]{0, 0, 0}, array);
    }

    static class ParameterPassing {
        static void primitiveByValue(int primitive) {
            primitive = 1;
        }

        static void immutableObjectByValue(String immutable) {
            immutable = "";
        }

        static void mutableObjectByValue(int[] array) {
            Arrays.fill(array, 0);
        }
    }

}
