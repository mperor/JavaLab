package pl.mperor.lab.java.design.pattern.behavioral.template.method;

public abstract class BackupProcess {

    public final void execute() {
        if (!canExecuteBackup()) {
            System.out.println("⛔ Backup not possible. Process terminated.");
            return;
        }
        performBackup();
        verifyBackup();
        cleanup();
    }

    protected boolean canExecuteBackup() {
        return true;
    }

    protected abstract void performBackup();

    protected abstract void verifyBackup();

    protected void cleanup() {
        System.out.println("🧹 Deleting old backup files...");
    }
}