package pl.mperor.lab.java.design.pattern.structural.facade;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

public class ComputerFacadeTest {

    @Test
    public void shouldAllowToRunComputerInVerySimpleWayHidingItsComplexity() {
        var out = TestUtils.setTempSystemOut();
        Computer pc = new ComputerFacade();
        pc.start();
        Assertions.assertNotNull(pc);
        Assertions.assertTrue(out.all().contains("CPU is executing"));
        TestUtils.resetSystemOut();
    }

}