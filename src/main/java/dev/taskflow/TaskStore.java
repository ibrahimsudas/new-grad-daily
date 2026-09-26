package dev.taskflow;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/** An in-memory {@link Task} store, keyed by an auto-incrementing id. */
public final class TaskStore {

  private final Map<Long, Task> tasks = new LinkedHashMap<>();
  private final AtomicLong nextId = new AtomicLong(1);
  private final Clock clock;

  public TaskStore() {
    this(Clock.systemUTC());
  }

  public TaskStore(Clock clock) {
    this.clock = clock;
  }

  /** Creates and stores a new task with the given title, returning it. */
  public Task add(String title) {
    long id = nextId.getAndIncrement();
    Task task = new Task(id, title, false, Instant.now(clock));
    tasks.put(id, task);
    return task;
  }

  /** Returns the task with the given id. */
  public Task get(long id) {
    Task task = tasks.get(id);
    if (task == null) {
      throw new TaskNotFoundException(id);
    }
    return task;
  }

  /** Returns all tasks, in the order they were added. */
  public List<Task> list() {
    return new ArrayList<>(tasks.values());
  }

  /** Marks the task with the given id as done and returns the updated task. */
  public Task complete(long id) {
    Task done = get(id).withDone(true);
    tasks.put(id, done);
    return done;
  }

  /** Removes the task with the given id. */
  public void delete(long id) {
    if (tasks.remove(id) == null) {
      throw new TaskNotFoundException(id);
    }
  }
}
