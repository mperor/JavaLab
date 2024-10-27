package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.jndi.CustomInitialContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Java 1.3 (May 2000)
 */
public class Java3 {

    @Test
    public void testJavaNamingAndDirectoryInterfaceAkaJNDILookup() throws NamingException {
        System.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, CustomInitialContextFactory.class.getName());
        String bindingName = "hello";
        String bindingResult = "Hello World!";

        Context context = new InitialContext();
        context.bind(bindingName, bindingResult);
        String lookupResult = (String) context.lookup(bindingName);
        Assertions.assertEquals(bindingResult, lookupResult);

        context.unbind(bindingName);
        Assertions.assertThrows(NamingException.class, () -> {
            context.lookup(bindingName);
        });
    }

    @Test
    public void testJavaSoundAPI() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        if (AudioSystem.getMixerInfo().length != 0)
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/test/resources/beep.wav"))) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                CountDownLatch latch = new CountDownLatch(1);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        latch.countDown();
                    }
                });
                clip.start();
                latch.await();
            }
    }
}