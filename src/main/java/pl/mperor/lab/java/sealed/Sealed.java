package pl.mperor.lab.java.sealed;

public sealed interface Sealed permits AlsoSealed, Final, NonSealed {

    default String sealedMethod() {
        return "sealed";
    }

}
