package dev.taskflow;

import picocli.CommandLine;

/** Entry point for the Taskflow CLI. */
public final class Main {

  static final String VERSION = "0.1.0-SNAPSHOT";

  private Main() {}

  public static void main(String[] args) {
    System.exit(new CommandLine(new TaskflowCli()).execute(args));
  }
}
