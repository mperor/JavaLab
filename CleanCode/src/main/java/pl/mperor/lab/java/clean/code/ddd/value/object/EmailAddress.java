package pl.mperor.lab.java.clean.code.ddd.value.object;

import java.util.regex.Pattern;

record EmailAddress(String email) {

    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    EmailAddress {
        if (email == null || PATTERN.asMatchPredicate().negate().test(email)) {
            throw new IllegalArgumentException("Invalid email address!");
        }
    }
}
