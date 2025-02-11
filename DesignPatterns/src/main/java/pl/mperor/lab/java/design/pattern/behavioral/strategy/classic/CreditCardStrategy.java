package pl.mperor.lab.java.design.pattern.behavioral.strategy.classic;

import java.time.LocalDate;

record CreditCardStrategy(String name, String cardNumber, String cvv, LocalDate dateOfExpiry) implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid by 💳 credit card!");
    }
}
