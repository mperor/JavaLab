package pl.mperor.lab.java.design.pattern.behavioral.template.method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

public class BackupProcessTemplateTest {

    @Test
    public void shouldAllowToExecuteBackupProcessForDifferentDataSources() {
        var out = TestUtils.setTempSystemOut();
        new DatabaseBackup().execute();
        Assertions.assertLinesMatch("""
                ===== Database Backup =====
                🗄️ Checking database connection...
                💾 Dumping database to SQL file...
                ✅ Checking database dump integrity...
                🧹 Deleting old backup files...
                """.lines(), out.lines().stream());

        out = TestUtils.setTempSystemOut();
        new LocalFileBackup().execute();
        Assertions.assertLinesMatch("""
                ===== File Backup =====
                🔍 Checking disk space for backup...
                📂 Copying files to backup directory...
                🔢 Comparing checksums of original and copied files...
                🧹 Deleting old backup files...
                """.lines(), out.lines().stream());

        TestUtils.resetSystemOut();
    }
}