package pl.mperor.lab.java.design.pattern.creational.builder.chain.outer;

import java.time.LocalDate;

public class Person {

    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String address;

    private Person(PersonBuilder builder) {
        this.firstName = builder.getFirstName();
        this.lastName = builder.getLastName();
        this.dateOfBirth = builder.getDateOfBirth();
        this.address = builder.getAddress();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public static Person build(PersonBuilder builder) {
        return new Person(builder);
    }

}
