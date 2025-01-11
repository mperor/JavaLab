package pl.mperor.lab.java.design.pattern.behavioral.eam;

import java.util.function.Supplier;

public class Retry {

    private final int retries;

    private Retry(int retries) {
        this.retries = retries;
    }

    public <T> T retry(Supplier<T> action) {
        Exception lastException = null;

        for (int i = 0; i < retries; i++) {
            try {
                return action.get();
            } catch (Exception e) {
                lastException = e;
            }
        }

        throw new NoRetriesRemainException("Failed to execute action after %s attempts".formatted(retries), lastException);
    }

    public static Retry of(int retries) {
        return new Retry(retries);
    }

    public static class NoRetriesRemainException extends RuntimeException {
        public NoRetriesRemainException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}