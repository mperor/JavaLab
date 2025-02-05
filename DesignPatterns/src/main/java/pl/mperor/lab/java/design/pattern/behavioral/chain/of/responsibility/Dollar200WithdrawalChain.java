package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

class Dollar200WithdrawalChain extends WithdrawalChain {

    Dollar200WithdrawalChain(int amount) {
        super(Banknote.$200, amount);
    }

}
