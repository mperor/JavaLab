package pl.mperor.lab.java.design.pattern.creational.builder.chain.outer;

import java.time.LocalDate;

public class PersonBuilder {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

    public PersonBuilder firstName(String name) {
        this.firstName = name;
        return this;
    }

    public PersonBuilder lastName(String name) {
        this.lastName = name;
        return this;
    }

    public PersonBuilder dateOfBirth(LocalDate date) {
        this.dateOfBirth = date;
        return this;
    }

    public PersonBuilder address(String address) {
        this.address = address;
        return this;
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

}