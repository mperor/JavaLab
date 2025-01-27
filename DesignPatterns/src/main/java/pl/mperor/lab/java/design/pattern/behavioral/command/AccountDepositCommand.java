package pl.mperor.lab.java.design.pattern.behavioral.command;

record AccountDepositCommand(Account account, double deposit) implements Command {

    @Override
    public void execute() {
        account.deposit(deposit);
    }

    @Override
    public Command reversed() {
        return new AccountWithdrawCommand(account, deposit);
    }
}
