package pl.mperor.lab.java.design.pattern.behavioral.visitor.validator;

class ThrowingValidationExceptionHandler implements ValidationExceptionHandler{

    @Override
    public void handle(ValidationException exception) {
        throw exception;
    }
}
