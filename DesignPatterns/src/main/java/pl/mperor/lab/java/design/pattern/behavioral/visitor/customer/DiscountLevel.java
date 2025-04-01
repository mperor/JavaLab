package pl.mperor.lab.java.design.pattern.behavioral.visitor.customer;

enum DiscountLevel {
    BRONZE(10),
    SILVER(20),
    GOLD(30);

    private final int percent;

    DiscountLevel(int percent) {
        this.percent = percent;
    }
}
