package pl.mperor.lab.java.design.pattern.behavioral.visitor.validator;

import java.util.regex.Pattern;

record Email(String address) {

    public Email {
        test(address, new ThrowingValidationExceptionHandler());
    }

    public static void test(String address, ValidationExceptionHandler handler) {
        new EmailValidator().validate(address, handler);
    }

    static class EmailValidator implements Validator<String> {

        public static final Pattern FORMAT_PATTERN = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");

        @Override
        public void validate(String address, ValidationExceptionHandler validationExceptionHandler) {
            if (FORMAT_PATTERN.asMatchPredicate().negate().test(address)) {
                validationExceptionHandler.handle(new InvalidFormatException());
            }
        }
    }

    static class InvalidFormatException extends ValidationException {
    }
}
