package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

enum Banknote {
    $10,
    $20,
    $50,
    $100,
    $200;

    int getValue() {
        return Integer.parseInt(this.name().substring(1));
    }
}
