package dev.taskflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class TaskStoreTest {

  private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");

  private final TaskStore store = new TaskStore(Clock.fixed(NOW, ZoneOffset.UTC));

  @Test
  void addAssignsIncrementingIds() {
    Task first = store.add("Buy milk");
    Task second = store.add("Walk the dog");

    assertThat(first.id()).isEqualTo(1);
    assertThat(second.id()).isEqualTo(2);
  }

  @Test
  void addStampsCreatedAtFromClockAndStartsNotDone() {
    Task task = store.add("Buy milk");

    assertThat(task.createdAt()).isEqualTo(NOW);
    assertThat(task.done()).isFalse();
  }

  @Test
  void addRejectsBlankTitle() {
    assertThatThrownBy(() -> store.add("  ")).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getReturnsStoredTask() {
    Task added = store.add("Buy milk");

    assertThat(store.get(added.id())).isEqualTo(added);
  }

  @Test
  void getThrowsForUnknownId() {
    assertThatThrownBy(() -> store.get(99))
        .isInstanceOf(TaskNotFoundException.class)
        .hasMessageContaining("99");
  }

  @Test
  void listReturnsAllTasksInAddedOrder() {
    Task first = store.add("Buy milk");
    Task second = store.add("Walk the dog");

    assertThat(store.list()).containsExactly(first, second);
  }

  @Test
  void listOnEmptyStoreReturnsEmptyList() {
    assertThat(store.list()).isEmpty();
  }

  @Test
  void completeMarksTaskDoneAndPersistsIt() {
    Task added = store.add("Buy milk");

    Task completed = store.complete(added.id());

    assertThat(completed.done()).isTrue();
    assertThat(store.get(added.id()).done()).isTrue();
  }

  @Test
  void completeThrowsForUnknownId() {
    assertThatThrownBy(() -> store.complete(99)).isInstanceOf(TaskNotFoundException.class);
  }

  @Test
  void deleteRemovesTask() {
    Task added = store.add("Buy milk");

    store.delete(added.id());

    assertThat(store.list()).isEmpty();
  }

  @Test
  void deleteThrowsForUnknownId() {
    assertThatThrownBy(() -> store.delete(99)).isInstanceOf(TaskNotFoundException.class);
  }
}
