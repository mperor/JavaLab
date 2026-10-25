package pl.mperor.lab.java.idiom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataOrientedProgrammingTest {

    @Test
    public void testEvalExhaustiveSwitchOverSealedRecords() {
        Expr sum = new Add(new Const(3), new Const(4));
        Expr product = new Mul(new Const(2), sum);

        Assertions.assertEquals(7, eval(sum));
        Assertions.assertEquals(14, eval(product));
    }

    @Test
    public void testGuardShortCircuitsMultiplicationByZero() {
        Expr neverEvaluated = new Add(new Const(1), new Const(1));
        Expr product = new Mul(new Const(0), neverEvaluated);

        Assertions.assertEquals(0, eval(product), "the `when c.value() == 0` guard matches before the generic Mul case");
    }

    // Exhaustive switch over a sealed hierarchy: the compiler proves every Expr
    // subtype is handled here, so a `default` branch would be dead code.
    private static double eval(Expr expr) {
        return switch (expr) {
            case Const(double value) -> value;
            case Mul(Const c, Expr _) when c.value() == 0 -> 0;
            case Mul(Expr left, Expr right) -> eval(left) * eval(right);
            case Add(Expr left, Expr right) -> eval(left) + eval(right);
        };
    }

    sealed interface Expr permits Const, Add, Mul {}

    record Const(double value) implements Expr {}

    record Add(Expr left, Expr right) implements Expr {}

    // Try adding `record Sub(Expr left, Expr right) implements Expr {}` to the
    // `permits` list above: eval()'s switch immediately stops compiling because
    // it no longer covers every Expr subtype. The sealed hierarchy turns a
    // missed case into a build failure instead of a silent runtime bug.
    record Mul(Expr left, Expr right) implements Expr {}
}
