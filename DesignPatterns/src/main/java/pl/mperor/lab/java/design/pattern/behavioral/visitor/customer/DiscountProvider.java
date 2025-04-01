package pl.mperor.lab.java.design.pattern.behavioral.visitor.customer;

public class DiscountProvider implements CustomerVisitor<DiscountLevel> {

    public DiscountLevel get(Customer customer) {
        return customer.accept(this);
    }

    @Override
    public DiscountLevel visit(Customer customer) {
        return switch (customer) {
            case RegularCustomer _ -> DiscountLevel.BRONZE;
            case CorporateCustomer _ -> DiscountLevel.SILVER;
            case PremiumCustomer _ -> DiscountLevel.GOLD;
        };
    }
}
