# JavaLab 🧪

[![Java CI with Gradle](https://github.com/mperor/JavaLab/actions/workflows/gradle.yml/badge.svg)](https://github.com/mperor/JavaLab/actions/workflows/gradle.yml)
[![Test Results](https://gist.githubusercontent.com/mperor/710a27220e46b738c4a2ccb888676388/raw/badge.svg)](https://github.com/mperor/JavaLab/actions/workflows/gradle.yml)

## Description 📄
**JavaLab** serves as an experimental playground and knowledge base for the Java programming language. 
The goal of this project is to maintain a set of tests that explore various nuances and complexities of Java code. 
Each test aims to provide clear examples and answers to common (and not-so-common) challenges developers face when working with Java.

## Modules 📦

- **[JavaReleases 📅](JavaReleases/README.md): Key features by version**
- **[FirstSteps 👣](FirstSteps/README.md): Java Basics**
- **[DesignPatterns 🧩](DesignPatterns/README.md): Design Patterns in Modern Java**
- **[CleanCode 🧼](CleanCode/README.md): Clean Code**
- **Common 🔧: Shared test utilities (e.g. capturing `System.out` in assertions)**

## Getting Started 🚀

Requires **JDK 25**.

```shell
./gradlew test                                                  # all modules
./gradlew :JavaReleases:test                                    # single module
./gradlew :JavaReleases:test --tests "pl.mperor.lab.java.Java8" # single test class
```

## How to Read It 📖

- **Tests are the documentation.** Each test method demonstrates one behavior and asserts it, so every example is verified on every build.
  Supporting classes live in `src/main`, but the story is told in `src/test`.
- **Start with what you need:**
  [FirstSteps](FirstSteps/README.md) for language fundamentals,
  [JavaReleases](JavaReleases/README.md) to see what each version added,
  [DesignPatterns](DesignPatterns/README.md) and [CleanCode](CleanCode/README.md) for design.
- **Each `JavaN` class starts with a header** listing the release's features
  (language, libraries and tools for Java 1.0–9, JEP numbers for Java 10+), followed by tests for the most relevant ones.
