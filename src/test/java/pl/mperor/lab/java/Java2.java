package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Java 1.2 (December 1998)
 */
public class Java2 {

    @Test
    public void testCollectionsApiBasics() {
        var list = List.of(1, 2, 3);
        var set = Set.of(1, 2, 3);
        var map = Map.of(1, "One", 2, "Two", 3, "Three");
        Assertions.assertEquals(1, list.getFirst());
        Assertions.assertTrue(set.contains(1));
        Assertions.assertEquals("Two", map.get(2));
    }

    @Test
    public void testSwingGUI() throws InterruptedException, InvocationTargetException {
        if (!GraphicsEnvironment.isHeadless())
            SwingUtilities.invokeAndWait(() -> {
                SwingForm form = new SwingForm();
                form.input.setText("Hi");
                form.button.doClick();
                Assertions.assertEquals("Your text: Hi!", form.label.getText());
            });
    }

}