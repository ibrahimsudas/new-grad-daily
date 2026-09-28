package dev.taskflow;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "done", description = "Mark a task as done.")
final class DoneCommand implements Callable<Integer> {

  @ParentCommand private TaskflowCli parent;

  @Parameters(index = "0", paramLabel = "ID", description = "the task id")
  private long id;

  @Override
  public Integer call() {
    Task task = parent.store().complete(id);
    System.out.printf("Completed task %d: %s%n", task.id(), task.title());
    return 0;
  }
}
