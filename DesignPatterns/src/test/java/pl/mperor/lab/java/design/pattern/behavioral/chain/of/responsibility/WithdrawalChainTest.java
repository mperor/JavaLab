package pl.mperor.lab.java.design.pattern.behavioral.chain.of.responsibility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WithdrawalChainTest {

    @Test
    public void testWithdrawBanknotes() {
        WithdrawalChain parentChain = new Dollar200WithdrawalChain(2);
        parentChain.next(new Dollar100WithdrawalChain(2))
                .next(new Dollar50WithdrawalChain(2))
                .next(new Dollar20WithdrawalChain(2))
                .next(new Dollar10WithdrawalChain(2));

        BanknotePocket pocket = parentChain.withdraw(360);

        Assertions.assertEquals(1, pocket.get(Banknote.$200));
        Assertions.assertEquals(1, pocket.get(Banknote.$100));
        Assertions.assertEquals(1, pocket.get(Banknote.$50));
        Assertions.assertEquals(0, pocket.get(Banknote.$20));
        Assertions.assertEquals(1, pocket.get(Banknote.$10));
    }
}