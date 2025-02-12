package pl.mperor.lab.java.design.pattern.creational.factory.abs;

interface StarterFactory {

    DatabaseConnection getDatabaseConnection();

    LoggingService getLoggingService();

    SecurityPolicy getSecurityPolicy();
}
