package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

class Dollar20WithdrawalChain extends WithdrawalChain {

    Dollar20WithdrawalChain(int amount) {
        super(Banknote.$20, amount);
    }
}
