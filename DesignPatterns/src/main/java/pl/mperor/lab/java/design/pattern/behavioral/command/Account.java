package pl.mperor.lab.java.design.pattern.behavioral.command;

class Account {

    private double balance;
    private boolean active;

    void deposit(double amount) {
        balance += amount;
    }

    void withdraw(double amount) {
        balance -= amount;
    }

    void changeActive() {
        active = !active;
    }

    double getBalance() {
        return balance;
    }

    boolean isActive() {
        return active;
    }
}
