package dev.taskflow;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "add", description = "Add a new task.")
final class AddCommand implements Callable<Integer> {

  @ParentCommand private TaskflowCli parent;

  @Parameters(paramLabel = "TITLE", arity = "1..*", description = "the task title")
  private String[] titleWords;

  @Override
  public Integer call() {
    Task task = parent.store().add(String.join(" ", titleWords));
    System.out.printf("Added task %d: %s%n", task.id(), task.title());
    return 0;
  }
}
