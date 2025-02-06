package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

class Dollar10WithdrawalChain extends WithdrawalChain {

    Dollar10WithdrawalChain(int amount) {
        super(Banknote.$10, amount);
    }
}
