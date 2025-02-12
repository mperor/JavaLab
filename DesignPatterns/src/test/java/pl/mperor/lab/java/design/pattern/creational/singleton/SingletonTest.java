package pl.mperor.lab.java.design.pattern.creational.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * The purpose of this test is to randomly enable one of the test methods to execute.
 * This is due to the fact that the tests involve the creation of a Singleton object.
 * Since the Singleton is initialized only once, executing multiple tests could cause issues
 * because subsequent tests would attempt to initialize the Singleton again, which is not allowed.
 * By randomly selecting and enabling just one test per run, we avoid such conflicts and ensure
 * the Singleton is properly handled without reinitialization.
 */
public class SingletonTest {

    private static int random = ThreadLocalRandom.current().nextInt(1, 4);

    static boolean isRandom1() {
        return random == 1;
    }

    static boolean isRandom2() {
        return random == 2;
    }

    static boolean isRandom3() {
        return random == 3;
    }

    @EnabledIf(value = "isRandom1")
    @Test
    public void testLazyInitialized() throws InterruptedException {
        assertLazyInitialized(() -> SingletonEnum.INSTANCE);
        assertLazyInitialized(SingletonEager::getInstance);
        assertLazyInitialized(SingletonHolder::getInstance);
        assertLazyInitialized(SingletonAtomic::getInstance);
        assertLazyInitialized(SingletonSerializable::getInstance);
    }

    private void assertLazyInitialized(Supplier<SingletonInstance> supplier) throws InterruptedException {
        long startedTime = System.currentTimeMillis();
        Thread.sleep(10);
        var instance = supplier.get();
        long instanceTime = instance.getCreationTime();
        Assertions.assertTrue(startedTime < instanceTime);
    }

    @EnabledIf(value = "isRandom2")
    @Test
    public void testSerializationSafe() throws IOException, ClassNotFoundException {
        assertSerializationSafe(() -> SingletonEnum.INSTANCE);
        assertSerializationSafe(SingletonSerializable::getInstance);
    }

    private void assertSerializationSafe(Supplier<SingletonInstance> supplier) throws IOException, ClassNotFoundException {
        SingletonInstance instance = supplier.get();

        var arrayOut = new ByteArrayOutputStream();
        try (var out = new ObjectOutputStream(arrayOut)) {
            out.writeObject(instance);
        }

        SingletonInstance deserializedInstance;
        try (var in = new ObjectInputStream(new ByteArrayInputStream(arrayOut.toByteArray()))) {
            deserializedInstance = (SingletonInstance) in.readObject();
        }

        Assertions.assertSame(instance, deserializedInstance, "Singleton instance should be the same after deserialization");
    }

    @EnabledIf(value = "isRandom3")
    @Test
    public void testThreadSafe() {
        assertThreadSafe(() -> SingletonEnum.INSTANCE);
        assertThreadSafe(SingletonEager::getInstance);
        assertThreadSafe(SingletonHolder::getInstance);
        assertThreadSafe(SingletonAtomic::getInstance);
        assertThreadSafe(SingletonSerializable::getInstance);
    }

    private void assertThreadSafe(Supplier<SingletonInstance> supplier) {
        var first = CompletableFuture.supplyAsync(() -> {
            var singleton = supplier.get();
            System.out.println("First ... " + singleton.getClass().getSimpleName());
            return singleton;
        });

        var second = CompletableFuture.supplyAsync(() -> {
            var singleton = supplier.get();
            System.out.println("Second ... " + singleton.getClass().getSimpleName());
            return singleton;
        });

        SingletonInstance firstResult = first.join();
        SingletonInstance secondResult = second.join();

        Assertions.assertSame(firstResult, secondResult);
        Assertions.assertEquals(firstResult.getCreationTime(), secondResult.getCreationTime());
    }
}