package pl.mperor.lab.java;

import module java.desktop;
import java.util.List; // single-type import wins over the module import below, resolving List as java.util.List

/// Demonstrates a caveat called out by JEP 511: `import module java.desktop` imports every package
/// exported by java.desktop (including `java.awt`, which defines `java.awt.List`) and, because
/// java.desktop is not java.base, also every package exported by java.base (including `java.util`,
/// which defines `java.util.List`). The bare name `List` is therefore ambiguous between the two
/// types; without the single-type import above, this file would not compile.
class JavaDesktopImportAmbiguity {

    final List<String> names = List.of("John Doe", "Jane Doe");

}
