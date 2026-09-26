package dev.taskflow;

/** Thrown when a lookup or mutation targets a task id that isn't in the store. */
public final class TaskNotFoundException extends RuntimeException {

  public TaskNotFoundException(long id) {
    super("no task with id " + id);
  }
}
