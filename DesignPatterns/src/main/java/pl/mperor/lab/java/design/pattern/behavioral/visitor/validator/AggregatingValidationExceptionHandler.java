package pl.mperor.lab.java.design.pattern.behavioral.visitor.validator;

import java.util.ArrayList;
import java.util.List;

class AggregatingValidationExceptionHandler implements ValidationExceptionHandler {

    private List<ValidationException> errors = new ArrayList<>();

    @Override
    public void handle(ValidationException exception) {
        errors.add(exception);
    }

    boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<ValidationException> getErrors() {
        return List.copyOf(errors);
    }
}
