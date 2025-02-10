package pl.mperor.lab.java.design.pattern.behavioral.strategy.classic;

record Item(String upcCode, String name, int priceInCents) {

    public double price() {
        return (double) priceInCents / 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;

        return upcCode.equals(item.upcCode);
    }

    @Override
    public int hashCode() {
        return upcCode.hashCode();
    }
}
