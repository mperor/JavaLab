package pl.mperor.lab.java.design.pattern.behavioral.visitor;

public sealed interface Customer permits CorporateCustomer, RegularCustomer, PremiumCustomer {

    default <T> T accept(CustomerVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
