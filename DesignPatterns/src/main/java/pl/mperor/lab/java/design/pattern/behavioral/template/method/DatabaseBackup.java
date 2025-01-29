package pl.mperor.lab.java.design.pattern.behavioral.template.method;

public class DatabaseBackup extends BackupProcess {

    DatabaseBackup() {
        System.out.println("===== Database Backup =====");
    }

    @Override
    protected boolean canExecuteBackup() {
        System.out.println("🗄️ Checking database connection...");
        return true;
    }

    @Override
    protected void performBackup() {
        System.out.println("💾 Dumping database to SQL file...");
    }

    @Override
    protected void verifyBackup() {
        System.out.println("✅ Checking database dump integrity...");
    }
}
