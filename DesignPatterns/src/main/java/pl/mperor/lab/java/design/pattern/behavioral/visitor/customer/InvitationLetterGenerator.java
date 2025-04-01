package pl.mperor.lab.java.design.pattern.behavioral.visitor.customer;

public class InvitationLetterGenerator implements CustomerVisitor<Letter> {

    public Letter generate(Customer customer) {
        return customer.accept(this);
    }

    @Override
    public Letter visit(Customer customer) {
        var message = switch (customer) {
            case RegularCustomer _ -> "👨 Welcome normal customer!";
            case CorporateCustomer _ -> "‍💼 Welcome business customer!";
            case PremiumCustomer _ -> "🧑‍💼 Welcome vip customer!";
        };
        return new Letter(message);
    }
}
