package pl.mperor.lab.java.design.pattern.creational.factory.abs;

public class DevStarterFactory implements StarterFactory {

    @Override
    public DatabaseConnection getDatabaseConnection() {
        return () -> System.out.println("Connecting to in-memory H2 database 🗄️.");
    }

    @Override
    public LoggingService getLoggingService() {
        return () -> System.out.println("Using DEV 🟩 logging service.");
    }

    @Override
    public SecurityPolicy getSecurityPolicy() {
        return () -> System.out.println("🔓 Applying no security.");
    }
}
