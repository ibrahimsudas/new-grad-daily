package dev.taskflow;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

/** Top-level command. Wires up the store and dispatches to a subcommand. */
@Command(
    name = "taskflow",
    mixinStandardHelpOptions = true,
    version = Main.VERSION,
    subcommands = {AddCommand.class, ListCommand.class, DoneCommand.class, RmCommand.class})
final class TaskflowCli implements Runnable {

  @Spec private CommandSpec spec;

  private final TaskStore store;

  TaskflowCli() {
    this(new TaskStore());
  }

  TaskflowCli(TaskStore store) {
    this.store = store;
  }

  TaskStore store() {
    return store;
  }

  @Override
  public void run() {
    spec.commandLine().usage(System.out);
  }
}
