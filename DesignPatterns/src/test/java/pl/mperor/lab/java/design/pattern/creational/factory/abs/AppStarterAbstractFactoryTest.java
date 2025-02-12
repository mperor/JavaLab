package pl.mperor.lab.java.design.pattern.creational.factory.abs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import java.util.stream.Stream;

public class AppStarterAbstractFactoryTest {

    @Test
    public void shouldAllowToCreateAppStarterForConcreteProfile() {
        var out = TestUtils.setTempSystemOut();

        AppStarter.startup(AppStarter.Profile.DEV);
        Assertions.assertLinesMatch(
                Stream.of(
                        "Connecting to in-memory H2 database 🗄️.",
                        "Using DEV 🟩 logging service.",
                        "🔓 Applying no security."
                ), out.all().lines()
        );

        AppStarter.startup(AppStarter.Profile.PROD);
        Assertions.assertLinesMatch(
                Stream.of(
                        "Connecting to PostgreSQL database 🗄️.",
                        "Using PROD 🟥 logging service.",
                        "🔒 Applying strict security policies."
                ), out.all().lines().skip(3)
        );

        TestUtils.resetSystemOut();
    }
}