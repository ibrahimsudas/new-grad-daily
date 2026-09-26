package dev.taskflow;

import java.time.Instant;
import java.util.Objects;

/** A single to-do item. */
public record Task(long id, String title, boolean done, Instant createdAt) {

  public Task {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("title must not be blank");
    }
    Objects.requireNonNull(createdAt, "createdAt must not be null");
  }

  /** Returns a copy of this task with {@code done} set to the given value. */
  public Task withDone(boolean done) {
    return new Task(id, title, done, createdAt);
  }
}
