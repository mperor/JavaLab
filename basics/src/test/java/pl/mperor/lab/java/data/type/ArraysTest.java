package pl.mperor.lab.java.data.type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArraysTest {

    @Test
    public void testArrayDefaultInitialization() {
        int[] array = new int[2];
        Assertions.assertEquals(0, array[0]);
        Assertions.assertArrayEquals(new int[]{0, 0}, array);
    }

    @Test
    public void testMultiDimensionalArray() {
        int[][] multi = new int[2][];
        Assertions.assertNull(multi[0]);
        multi[0] = new int[1];
        multi[1] = new int[2];
        Assertions.assertArrayEquals(new int[][]{{0}, {0, 0}}, multi);
    }

    @Test
    public void testSquareMatrix() {
        int[][] matrix = new int[2][2];
        Assertions.assertArrayEquals(new int[][]{{0, 0}, {0, 0}}, matrix);
    }
}
