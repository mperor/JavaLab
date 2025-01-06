package pl.mperor.lab.java.lang.element;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PackageTest {

    @Test
    public void testPackage() {
        Assertions.assertEquals("pl.mperor.lab.java.lang.element",
                PackageTest.class.getPackageName(),
                "Package is a box for classes");

        Assertions.assertEquals("pl.mperor.lab.java.lang.element.PackageTest",
                PackageTest.class.getName(),
                "Package + class name => unique address");

        Assertions.assertEquals("pl.mperor",
                Arrays.stream("mperor.pl".split("\\."))
                        .sorted((_, _) -> -1)
                        .collect(Collectors.joining(".")),
                "Packages use the reversed domain name");
    }
}
