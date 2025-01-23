package pl.mperor.lab.java.design.pattern.creational.factory.abs;

public interface StarterFactory {

    DatabaseConnection getDatabaseConnection();

    LoggingService getLoggingService();

    SecurityPolicy getSecurityPolicy();
}
