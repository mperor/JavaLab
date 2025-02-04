package pl.mperor.lab.java.design.pattern.behavioral.visitor;

public interface CustomerVisitor<T> {

    T visit(Customer customer);
}
