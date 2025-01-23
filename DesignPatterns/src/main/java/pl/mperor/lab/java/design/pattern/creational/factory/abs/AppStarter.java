package pl.mperor.lab.java.design.pattern.creational.factory.abs;

public class AppStarter {

    public static void startup(Profile profile) {
        StarterFactory starter = switch (profile) {
            case PROD -> new ProdStarterFactory();
            case DEV -> new DevStarterFactory();
        };

        starter.getDatabaseConnection().connect();
        starter.getLoggingService().init();
        starter.getSecurityPolicy().applyPolicy();
    }

    public enum Profile {
        PROD,
        DEV;
    }
}
