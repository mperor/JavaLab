package pl.mperor.lab.java.design.pattern.behavioral.visitor.customer;

public interface CustomerVisitor<T> {

    T visit(Customer customer);
}
