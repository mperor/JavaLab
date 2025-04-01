package pl.mperor.lab.java.design.pattern.behavioral.visitor.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidatorVisitorTest {

    @Test
    public void testValidatePasswordAndEmailWithExceptionAggregation() {
        var aggregationHandler = new AggregatingValidationExceptionHandler();
        Password.test("xyz", aggregationHandler);
        Email.test("user1$gmail.com", aggregationHandler);
        Assertions.assertTrue(aggregationHandler.hasErrors());
        Assertions.assertEquals(aggregationHandler.getErrors().size(), 2);
    }

    @Test
    public void testValidatePasswordAndEmailWithExceptionThrowing() {
        Assertions.assertThrows(Password.InvalidLengthException.class, () -> new Password("xyz"));
        Assertions.assertThrows(Email.InvalidFormatException.class, () -> new Email("user1$gmail.com"));

        var throwingHandler = new ThrowingValidationExceptionHandler();
        Assertions.assertThrows(Password.InvalidLengthException.class, () -> Password.test("xyz", throwingHandler));
        Assertions.assertThrows(Email.InvalidFormatException.class, () -> Email.test("user1$gmail.com", throwingHandler));
    }
}