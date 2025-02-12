package pl.mperor.lab.java.design.pattern.creational.builder.chain.nested;

import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private int id;
    private String name;
    private String password;
    private String email;

    private User() {
    }

    // Deep copy is required here!
    private User(User user) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.name = user.name;
        this.password = user.password;
        this.email = user.email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final User user;

        private Builder() {
            this.user = new User();
        }

        public Builder name(String name) {
            this.user.name = name;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public User build() {
            return new User(user);
        }
    }
}
