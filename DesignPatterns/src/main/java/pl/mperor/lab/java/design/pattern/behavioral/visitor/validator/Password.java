package pl.mperor.lab.java.design.pattern.behavioral.visitor.validator;

record Password(String value) {

    public Password {
        Password.test(value, new ThrowingValidationExceptionHandler());
    }

    public static void test(String password, ValidationExceptionHandler validationExceptionHandler) {
        new PasswordValidator().validate(password, validationExceptionHandler);
    }

    static class PasswordValidator implements Validator<String> {

        @Override
        public void validate(String password, ValidationExceptionHandler validationExceptionHandler) {
            if (password.length() < 8 || password.length() > 16) {
                validationExceptionHandler.handle(new InvalidLengthException());
            }
        }
    }

    static class InvalidLengthException extends ValidationException {
    }
}
