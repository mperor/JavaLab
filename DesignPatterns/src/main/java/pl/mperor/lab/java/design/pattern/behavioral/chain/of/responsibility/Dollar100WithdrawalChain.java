package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

class Dollar100WithdrawalChain extends WithdrawalChain {

    Dollar100WithdrawalChain(int amount) {
        super(Banknote.$100, amount);
    }
}
