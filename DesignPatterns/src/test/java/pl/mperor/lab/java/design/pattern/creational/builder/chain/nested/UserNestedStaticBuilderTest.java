package pl.mperor.lab.java.design.pattern.creational.builder.chain.nested;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserNestedStaticBuilderTest {

    @Test
    public void userBuilderShouldAllowToBuildUser() {
        User user = User.builder().build();
        Assertions.assertNotNull(user);
        Assertions.assertTrue(user.getId() >= 1);
        Assertions.assertNull(user.getName());
        Assertions.assertNull(user.getPassword());
    }

    @Test
    public void userBuilderShouldAllowToBuildCustomUser() {
        User custom = User.builder()
                .name("user")
                .password("{noop}password")
                .email("user@example.com")
                .build();

        Assertions.assertTrue(custom.getId() >= 1);
        Assertions.assertEquals("user", custom.getName());
        Assertions.assertNotNull(custom.getPassword());
        Assertions.assertEquals("user@example.com", custom.getEmail());
    }

    @Test
    public void userBuilderShouldAllowToBuildGroupOfUsers() {
        var builder = User.builder();
        User u1 = builder
                .name("Anonymous")
                .email("anonymous@example.com")
                .build();
        Assertions.assertEquals("Anonymous", u1.getName());
        Assertions.assertEquals("anonymous@example.com", u1.getEmail());

        User u2 = builder
                .email("admin@example.com")
                .build();
        Assertions.assertEquals("Anonymous", u2.getName());
        Assertions.assertEquals("admin@example.com", u2.getEmail());

        User u3 = User.builder()
                .email("noname@example.com")
                .build();
        Assertions.assertNull(u3.getName());
        Assertions.assertEquals("noname@example.com", u3.getEmail());

        Assertions.assertTrue(u1.getId() < u2.getId() && u2.getId() < u3.getId());
    }

}