package pl.mperor.lab.java.design.pattern.behavioral.strategy.classic;

class Shopping {

    private PaymentStrategy strategy;

    Shopping(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    void pay(ShoppingCart cart) {
        pay(cart, strategy);
    }

    void pay(ShoppingCart cart, PaymentStrategy strategy) {
        double total = cart.computeTotal();
        strategy.pay(total);
    }
}
