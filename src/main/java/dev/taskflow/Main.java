package dev.taskflow;

/** Entry point for the Taskflow CLI. */
public final class Main {

  static final String VERSION = "0.1.0-SNAPSHOT";

  private Main() {}

  public static void main(String[] args) {
    System.out.println(run(args));
  }

  static String run(String[] args) {
    if (args.length > 0 && "--version".equals(args[0])) {
      return VERSION;
    }
    return "taskflow: no command given (try --version)";
  }
}
