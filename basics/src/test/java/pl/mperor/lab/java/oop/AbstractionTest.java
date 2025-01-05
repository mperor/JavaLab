package pl.mperor.lab.java.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.oop.abstraction.Template;

public class AbstractionTest {

    @Test
    public void testObjectVsClass() {
        Template type = Template.getInstance();
        Assertions.assertNotNull(type);
        Assertions.assertInstanceOf(Template.class, type);
    }
}
