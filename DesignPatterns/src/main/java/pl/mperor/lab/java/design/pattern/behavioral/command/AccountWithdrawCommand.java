package pl.mperor.lab.java.design.pattern.behavioral.command;

record AccountWithdrawCommand(Account account, double withdraw) implements Command {

    @Override
    public void execute() {
        account.withdraw(withdraw);
    }

    @Override
    public Command reversed() {
        return new AccountDepositCommand(account, withdraw);
    }
}
