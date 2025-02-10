package pl.mperor.lab.java.design.pattern.behavioral.strategy.classic;

public record PaypalStrategy(String emailId, String password) implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid with 📱 paypal!");
    }
}
