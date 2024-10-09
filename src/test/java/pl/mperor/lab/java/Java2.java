package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Java 1.2 (December 1998)
 */
public class Java2 {

    @Test
    public void testCollectionsApiBasics() {
        var list = Arrays.asList(1, 2, 3);
        var set = new HashSet<>(list);
        var map = Collections.singletonMap(2, "Two");
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