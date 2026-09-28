package dev.taskflow;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "rm", description = "Remove a task.")
final class RmCommand implements Callable<Integer> {

  @ParentCommand private TaskflowCli parent;

  @Parameters(index = "0", paramLabel = "ID", description = "the task id")
  private long id;

  @Override
  public Integer call() {
    Task task = parent.store().get(id);
    parent.store().delete(id);
    System.out.printf("Removed task %d: %s%n", task.id(), task.title());
    return 0;
  }
}
