package pl.mperor.lab.java.design.pattern.behavioral.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandRegistryTest {

    @Test
    public void testUndoRedoImplementationWithCommandPattern() {
        Account account = new Account();
        CommandRegister commander = new CommandRegister();
        commander.execute(account::changeActive);
        commander.execute(new AccountDepositCommand(account, 10));
        Assert.that(account).active(true).balance(10);

        commander.undo();
        Assert.that(account).active(true).balance(0);

        commander.undo();
        Assert.that(account).active(false).balance(0);

        commander.redo();
        Assert.that(account).active(true).balance(0);

        commander.redo();
        Assert.that(account).active(true).balance(10);
    }

    static class Assert {

        private Account account;

        Assert(Account account) {
            this.account = account;
        }

        Assert active(boolean active) {
            Assertions.assertEquals(active, account.isActive());
            return this;
        }

        Assert balance(double balance) {
            Assertions.assertEquals(balance, account.getBalance());
            return this;
        }

        static Assert that(Account account) {
            return new Assert(account);
        }
    }
}