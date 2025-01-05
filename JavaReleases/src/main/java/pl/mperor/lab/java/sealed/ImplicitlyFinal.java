package pl.mperor.lab.java.sealed;

public record ImplicitlyFinal() implements Sealed {

    public String implicitlyFinalMethod() {
        return "implicitly final";
    }
}
