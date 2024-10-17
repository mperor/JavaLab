package pl.mperor.lab.java.sealed;

public sealed class AlsoSealed implements Sealed permits AlsoFinal {

    public String alsoSealedMethod() {
        return "sealed";
    }
}
