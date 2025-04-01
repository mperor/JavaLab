package pl.mperor.lab.java.design.pattern.behavioral.visitor.validator;

interface Validator<T> {

    void validate(T toValid, ValidationExceptionHandler validationExceptionHandler);
}
