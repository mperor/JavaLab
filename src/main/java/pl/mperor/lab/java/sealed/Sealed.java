package pl.mperor.lab.java.sealed;

public sealed interface Sealed permits AlsoSealed, NonSealed, Final {

    default String sealedMethod() {
        return "sealed";
    }

}
