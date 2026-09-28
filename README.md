# Taskflow

A task tracker written in Java 21. It starts as a command-line app and grows
into a REST API. It's a side project I use to get better at backend
development.

## Features

- Add, list, complete and remove tasks from the command line

## Getting started

Requires Java 21. The Maven wrapper takes care of the rest.

```bash
./mvnw -B verify        # build and run the tests
java -cp target/classes dev.taskflow.Main --version

java -cp target/classes dev.taskflow.Main add "Buy milk"
java -cp target/classes dev.taskflow.Main list
java -cp target/classes dev.taskflow.Main done 1
java -cp target/classes dev.taskflow.Main rm 1
```

Before pushing, run `bash scripts/check.sh` to build, test and check formatting.

## Tech

Java 21 · Maven · JUnit 5 · AssertJ · Spotless (google-java-format) · picocli
