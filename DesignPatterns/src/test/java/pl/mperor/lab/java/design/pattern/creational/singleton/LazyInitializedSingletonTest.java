package pl.mperor.lab.java.design.pattern.creational.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

public class LazyInitializedSingletonTest {

    @Test
    public void shouldOnlyAllowToLazyCreateOneInstanceOfSingleton() {
        var first = CompletableFuture.supplyAsync(() -> {
            System.out.println("First ...");
            return LazyInitializedSingleton.getInstance();
        });
        var second = CompletableFuture.supplyAsync(() -> {
            System.out.println("Second  ...");
            return LazyInitializedSingleton.getInstance();
        });

        LazyInitializedSingleton firstResult = first.join();
        LazyInitializedSingleton secondResult = second.join();

        Assertions.assertSame(firstResult, secondResult);
        Assertions.assertEquals(firstResult.getTime(), secondResult.getTime());
    }
}