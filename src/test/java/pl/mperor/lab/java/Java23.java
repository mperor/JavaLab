package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

/// Java 23 (September 2024)
/// [JDK 23](https://openjdk.org/projects/jdk/23)
///
/// - STANDARD FEATURES:
///     - 467:	Markdown Documentation Comments
///     - 471:	Deprecate the Memory-Access Methods in sun.misc.Unsafe for Removal
///     - 474:	ZGC: Generational Mode by Default
///
/// - PREVIEW & INCUBATOR:
///     - 476:	Module Import Declarations (Preview)
///     - 455:	Primitive Types in Patterns, instanceof, and switch (Preview)
///     - 466:	Class-File API (Second Preview)
///     - 473:	Stream Gatherers (Second Preview)
///     - 482:	Flexible Constructor Bodies (Second Preview)
///     - 477:	Implicitly Declared Classes and Instance Main Methods (Third Preview)
///     - 480:	Structured Concurrency (Third Preview)
///     - 481:	Scoped Values (Third Preview)
///     - 469:	Vector API (Eighth Incubator)
public class Java23 {

    /// This is a **Markdown** JavaDoc (starts with ///).
    /// Markdown Documentation Comments are described in this [JEP](https://openjdk.org/jeps/467).
    /// It supports *italic*, **bold**, and `code` formatting.
    ///
    /// As well:
    /// - code block:
    /// ```java
    /// void main(){
    ///     println("Unnamed entry point!");
    ///}
    ///```
    /// - table:
    ///
    /// | JDK  | Year |
    /// |------|------|
    /// | 17   | 2021 |
    /// | 21   | 2023 |
    /// | 25   | 2025 |
    @Test
    public void testMarkdownDocumentationComments() {
    }

    @Test
    public void testSunMiscUnsafeDeprecated() {
        Assertions.assertThrows(SecurityException.class, Unsafe::getUnsafe);
    }

}