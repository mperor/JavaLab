package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.sealed.*;

/**
 * Java 15 (September 2020)
 */
public class Java15 {

    @Test
    public void testSealedClasses() {
        Assertions.assertTrue(Sealed.class.isSealed());
        Assertions.assertFalse(NonSealed.class.isSealed());
        Assertions.assertFalse(Final.class.isSealed());
        Assertions.assertTrue(AlsoSealed.class.isSealed());
        Assertions.assertFalse(AlsoFinal.class.isSealed());

        Assertions.assertEquals("final", switchSealed(new Final()));
        Assertions.assertEquals("non-sealed", switchSealed(new NonSealed()));
        Assertions.assertEquals("sealed", switchSealed(new AlsoSealed()));
    }

    private String switchSealed(Sealed sealed) {
        return switch (sealed) {
            case AlsoSealed a -> a.alsoSealedMethod();
            case Final f -> f.finalMethod();
            case NonSealed ns -> ns.nonSealedMethod();
        };
    }

}