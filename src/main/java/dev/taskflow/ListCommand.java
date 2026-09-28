package dev.taskflow;

import java.util.List;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "list", description = "List all tasks.")
final class ListCommand implements Callable<Integer> {

  @ParentCommand private TaskflowCli parent;

  @Override
  public Integer call() {
    List<Task> tasks = parent.store().list();
    if (tasks.isEmpty()) {
      System.out.println("No tasks yet.");
      return 0;
    }
    for (Task task : tasks) {
      System.out.printf("%s %d  %s%n", task.done() ? "[x]" : "[ ]", task.id(), task.title());
    }
    return 0;
  }
}
