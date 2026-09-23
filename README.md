# Taskflow

A task tracker written in Java 21. It starts as a command-line app and grows
into a REST API. It's a side project I use to get better at backend
development.

## Features

Work in progress. Features are listed here as they land.

## Getting started

Requires Java 21. The Maven wrapper takes care of the rest.

```bash
./mvnw -B verify        # build and run the tests
java -cp target/classes dev.taskflow.Main --version
```

Before pushing, run `bash scripts/check.sh` to build, test and check formatting.

## Tech

Java 21 · Maven · JUnit 5 · AssertJ · Spotless (google-java-format)
