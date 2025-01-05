package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

/// Java 13™ (September 2019)
/// [JDK 13](https://openjdk.org/projects/jdk/13)
///
/// - STANDARD FEATURES:
///     - 353:	Reimplement the Legacy Socket API (`-Djdk.net.usePlainSocketImpl`)
///     - 350:	Dynamic CDS (Class-Data Sharing) Archives (`-XX:ArchiveClassesAtExit=hello.jsa`)
///     - 351:	ZGC: Uncommit Unused Memory (`-XX:+UseZGC`)
///
/// - PREVIEW & INCUBATOR:
///     - 354:	Switch Expressions (Preview)
///     - 355:	Text Blocks (Preview)
public class Java13 {

    @Test
    public void testServerSocketImpl() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8013)) {
            Assertions.assertNotNull(serverSocket);
        }
    }

}
