package pl.mperor.lab.java.design.pattern.creational.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class SingletonTest {

    @Test
    public void shouldOnlyAllowToCreateOneInstanceOfSingleton() throws InterruptedException {
        var instance = Singleton.getInstance();
        long time = instance.getTime();

        TimeUnit.MILLISECONDS.sleep(50);
        long timeAfterBreak = Singleton.getInstance().getTime();

        Assertions.assertSame(instance, Singleton.getInstance());
        Assertions.assertEquals(time, timeAfterBreak);
    }

}