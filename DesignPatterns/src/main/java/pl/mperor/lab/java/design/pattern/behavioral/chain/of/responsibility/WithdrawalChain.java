package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

abstract class WithdrawalChain {

    protected final Banknote banknote;
    protected int banknoteAmount;
    protected WithdrawalChain nextChain;

    WithdrawalChain(Banknote banknote, int amount) {
        this.banknote = banknote;
        this.banknoteAmount = amount;
    }

    BanknotePocket withdraw(int currency) {
        int banknoteValue = banknote.getValue();
        int restValue = currency;
        int banknoteCounter = 0;

        while (restValue - banknoteValue >= 0 && banknoteAmount > 0) {
            restValue -= banknoteValue;
            banknoteCounter++;
        }

        BanknotePocket pocket = nextChain == null
                ? new BanknotePocket()
                : nextChain.withdraw(restValue);

        pocket.add(banknote, banknoteCounter);
        return pocket;
    }

    WithdrawalChain next(WithdrawalChain next) {
        nextChain = next;
        return next;
    }
}