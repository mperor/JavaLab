package pl.mperor.lab.java.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.oop.encapsulation.EncapsulatedVisitCounter;
import pl.mperor.lab.java.oop.encapsulation.PublicVisitCounter;

public class EncapsulationTest {

    @Test
    public void testEncapsulation() {
        var publicVisitCounter = new PublicVisitCounter();
        publicVisitCounter.increment();
        Assertions.assertEquals(1, publicVisitCounter.visitCounter);
        Assertions.assertEquals(-1, publicVisitCounter.visitCounter = -1);

        var encapsulatedVisitCounter = new EncapsulatedVisitCounter();
        encapsulatedVisitCounter.increment();
        Assertions.assertEquals(1, encapsulatedVisitCounter.getVisitCounter());
    }

}
