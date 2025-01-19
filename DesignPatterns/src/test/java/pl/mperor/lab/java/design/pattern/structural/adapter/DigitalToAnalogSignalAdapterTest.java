package pl.mperor.lab.java.design.pattern.structural.adapter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

public class DigitalToAnalogSignalAdapterTest {

    @Test
    public void adapterShouldAllowToTransformDigitalSignalToAnalogSignal() {
        var out = TestUtils.setTempSystemOut();

        var monitor = new ComputerMonitor();
        var videoCard = new VideoCard();

        videoCard.render(digitalSignal ->
                monitor.display(new DigitalToAnalogSignalAdapter(digitalSignal))
        );

        var outLines = out.lines();
        Assertions.assertEquals("🖥️ Computer monitor displaying ...", outLines.getFirst());
        Assertions.assertEquals("Video card rendered signal 🎲", outLines.getSecond());

        TestUtils.resetSystemOut();
    }

}