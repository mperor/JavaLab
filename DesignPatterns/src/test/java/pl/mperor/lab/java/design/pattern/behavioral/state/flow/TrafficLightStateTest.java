package pl.mperor.lab.java.design.pattern.behavioral.state.flow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import java.util.stream.Stream;

public class TrafficLightStateTest {

    @Test
    public void testTrafficLightStateChanges() {
        var out = TestUtils.setTempSystemOut();
        var trafficLight = new TrafficLight();

        Stream.generate(() -> {
            trafficLight.display();
            trafficLight.change();
            return trafficLight;
        }).limit(4).count();

        Assertions.assertLinesMatch("""
                🔴 STOP! The light is RED.
                🟢 GO! The light is GREEN.
                🟡 Caution! The light is YELLOW.
                🔴 STOP! The light is RED.
                """.lines(), out.lines().stream());

        TestUtils.resetSystemOut();
    }
}