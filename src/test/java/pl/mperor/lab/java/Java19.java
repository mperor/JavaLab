package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.TestUtils;
import pl.mperor.lab.TestUtils.ReadableOut;

/**
 * Java 19 (September 2022)
 */
public class Java19 {

    @Test
    public void testRecordPatternDeconstruct() {
        record Point(int x, int y) {}
        record LineSegment(Point start, Point end) {}
        Object obj = new LineSegment(new Point(0,1), new Point(1, 2));

        if(obj instanceof LineSegment(Point(int x, int y), Point end)) {
            Assertions.assertEquals(0, x);
            Assertions.assertEquals(1, y);
            Assertions.assertEquals(new Point (1,2), end);
        }
    }

    @Test
    public void testVirtualThreads() throws InterruptedException {
        ReadableOut out = TestUtils.setTempSystemOut();
        Thread virtualThread = Thread.startVirtualThread(() ->
                System.out.print("Hello from Virtual Thread!")
        );
        virtualThread.join();
        TestUtils.resetSystemOut();

        Assertions.assertTrue(virtualThread.isDaemon() && virtualThread.isVirtual());
        Assertions.assertEquals(Thread.NORM_PRIORITY, virtualThread.getPriority());
        Assertions.assertEquals("Hello from Virtual Thread!", out.all());
    }

}