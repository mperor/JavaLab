package pl.mperor.lab.java.design.pattern.behavioral.template.method;

public class LocalFileBackup extends BackupProcess {

    LocalFileBackup() {
        System.out.println("===== File Backup =====");
    }

    @Override
    protected boolean canExecuteBackup() {
        System.out.println("🔍 Checking disk space for backup...");
        return true;
    }

    @Override
    protected void performBackup() {
        System.out.println("📂 Copying files to backup directory...");
    }

    @Override
    protected void verifyBackup() {
        System.out.println("🔢 Comparing checksums of original and copied files...");
    }
}
