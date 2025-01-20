package pl.mperor.lab.java.design.pattern.structural.bridge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

public class VehicleBridgeTest {

    @Test
    public void shouldAllowToCreateVehiclesWithDifferentEngines() {
        var out = TestUtils.setTempSystemOut();

        var diselEngineCar = new Car(new DieselEngine());
        var petrolEngineBus = new Bus(new PetrolEngine());

        diselEngineCar.run();
        petrolEngineBus.run();

        var outLines = out.lines();
        Assertions.assertEquals("Diesel engine 🟢 has been started!", outLines.getFirst());
        Assertions.assertEquals("Car 🚗 is running!", outLines.getSecond());
        Assertions.assertEquals("Petrol engine 🟢 has been started!", outLines.getThird());
        Assertions.assertEquals("Bus 🚌 is running!", outLines.getForth());

        TestUtils.resetSystemOut();
    }
}