package pl.mperor.lab.java.design.pattern.creational.factory.abs;

class ProdStarterFactory implements StarterFactory {

    @Override
    public DatabaseConnection getDatabaseConnection() {
        return () -> System.out.println("Connecting to PostgreSQL database 🗄️.");
    }

    @Override
    public LoggingService getLoggingService() {
        return () -> System.out.println("Using PROD 🟥 logging service.");
    }

    @Override
    public SecurityPolicy getSecurityPolicy() {
        return () -> System.out.println("🔒 Applying strict security policies.");
    }
}
