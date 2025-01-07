package pl.mperor.lab.java.design.pattern.structural.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import pl.mperor.lab.common.TestUtils;

public class ServiceProviderTest {

    @Test
    public void testServiceUsingProxyUnderneath() {
        var out = TestUtils.setTempSystemOut();
        var service = ServiceProvider.INSTANCE.get();
        Assertions.assertTrue(StringUtils.isBlank(out.all()),
                "Lazy loading service should not be initialized!");
        Assertions.assertInstanceOf(ServiceProxy.class, service);

        service.execute();
        var outLines = out.lines();
        Assertions.assertEquals(outLines.getFirst(), "Before executing service method!");
        Assertions.assertEquals(outLines.getSecond(), "Service has been initialized!");
        Assertions.assertEquals(outLines.getThird(), "Method has been executed!");
        Assertions.assertEquals(outLines.getForth(), "After executing service method!");
        TestUtils.resetSystemOut();
    }

}
