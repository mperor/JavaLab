package pl.mperor.lab.java.design.pattern.creational.builder.chain.outer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

public class PersonOuterBuilderTest {

    @Test
    public void personShouldBeCreatedByPersonBuilder() {
        Person person = Person.build(new PersonBuilder()
                .firstName("Jack")
                .lastName("Bell")
                .address("New York")
                .dateOfBirth(LocalDate.of(1990, Month.JANUARY, 1)));

        Assertions.assertNotNull(person);
        Assertions.assertEquals("Jack", person.getFirstName());
        Assertions.assertEquals("Bell", person.getLastName());
        Assertions.assertEquals("New York", person.getAddress());
        Assertions.assertEquals("1990-01-01", person.getDateOfBirth().toString());
    }


}