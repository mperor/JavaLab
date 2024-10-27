package pl.mperor.lab.java.sealed;

public sealed interface Sealed permits AlsoSealed, Final, ImplicitlyFinal, NonSealed {

    default String sealedMethod() {
        return "sealed";
    }

}
