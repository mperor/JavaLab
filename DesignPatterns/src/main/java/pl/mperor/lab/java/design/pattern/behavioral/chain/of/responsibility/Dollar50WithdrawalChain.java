package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

class Dollar50WithdrawalChain extends WithdrawalChain {

    Dollar50WithdrawalChain(int amount) {
        super(Banknote.$50, amount);
    }
}
