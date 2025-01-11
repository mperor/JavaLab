package pl.mperor.lab.java.design.pattern.behavioral.eam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import java.util.List;
import java.util.stream.IntStream;

public class ExecuteAroundMethodTest {

    @Test
    public void shouldAllowToUseExecuteAroundMethod() {
        var out = TestUtils.setTempSystemOut();
        executeAround(() -> System.out.println("action"));
        Assertions.assertLinesMatch(List.of("setup", "action", "tear down"), out.lines());
        TestUtils.resetSystemOut();
    }

    private static void executeAround(Runnable action) {
        System.out.println("setup");
        action.run();
        System.out.println("tear down");
    }

    @Test
    public void testRetry() {
        var exception = Assertions.assertThrows(Retry.NoRetriesRemainException.class, () -> Retry.of(3).retry(() -> 1 / 0));
        Assertions.assertInstanceOf(ArithmeticException.class, exception.getCause());
        Assertions.assertEquals(10, Retry.of(1).retry(() -> IntStream.rangeClosed(1, 4).sum()));
    }

}
