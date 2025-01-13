package pl.mperor.lab.java.design.pattern.creational.builder.chain.inner;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private int id;
    private String description;
    private boolean done;

    private Task() {
    }

    private Task(Task task) {
        this.id = ID_GENERATOR.getAndIncrement();
        // Deep copy is required here!
        this.description = task.description;
        this.done = task.done;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public static Builder builder() {
        return new Task().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder description(String description) {
            Task.this.description = description;
            return this;
        }

        public Builder done(boolean done) {
            Task.this.done = done;
            return this;
        }

        public Task build() {
            return new Task(Task.this);
        }
    }

}
