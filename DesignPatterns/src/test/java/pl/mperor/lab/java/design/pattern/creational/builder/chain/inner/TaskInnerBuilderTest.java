package pl.mperor.lab.java.design.pattern.creational.builder.chain.inner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskInnerBuilderTest {

    @Test
    public void builderShouldAllowToBuildTask() {
        Task task = Task.builder()
                .build();

        Assertions.assertNotNull(task);
        Assertions.assertTrue(task.getId() >= 1);
        Assertions.assertNull(task.getDescription());
        Assertions.assertFalse(task.isDone());
    }

    @Test
    public void builderShouldAllowToBuildDoneTasks() {
        var builder = Task.builder()
                .done(true);

        Task t1 = builder
                .description("First task")
                .build();

        Task t2 = builder
                .description("Second task")
                .build();

        Assertions.assertEquals("First task", t1.getDescription());
        Assertions.assertEquals("Second task", t2.getDescription());
        Assertions.assertTrue(t1.isDone() && t2.isDone());
        Assertions.assertNotEquals(t1.getId(), t2.getId());
    }

}