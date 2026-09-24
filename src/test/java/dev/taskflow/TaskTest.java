package dev.taskflow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class TaskTest {

  private static final Instant NOW = Instant.parse("2026-01-01T00:00:00Z");

  @Test
  void createsTaskWithGivenFields() {
    Task task = new Task(1, "Buy milk", false, NOW);

    assertThat(task.id()).isEqualTo(1);
    assertThat(task.title()).isEqualTo("Buy milk");
    assertThat(task.done()).isFalse();
    assertThat(task.createdAt()).isEqualTo(NOW);
  }

  @Test
  void rejectsNullTitle() {
    assertThatThrownBy(() -> new Task(1, null, false, NOW))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void rejectsEmptyTitle() {
    assertThatThrownBy(() -> new Task(1, "", false, NOW))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void rejectsBlankTitle() {
    assertThatThrownBy(() -> new Task(1, "   ", false, NOW))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void rejectsNullCreatedAt() {
    assertThatThrownBy(() -> new Task(1, "Buy milk", false, null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void tasksWithSameFieldsAreEqual() {
    Task a = new Task(1, "Buy milk", false, NOW);
    Task b = new Task(1, "Buy milk", false, NOW);

    assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
  }

  @Test
  void tasksWithDifferentIdAreNotEqual() {
    Task a = new Task(1, "Buy milk", false, NOW);
    Task b = new Task(2, "Buy milk", false, NOW);

    assertThat(a).isNotEqualTo(b);
  }

  @Test
  void withDoneReturnsCopyWithFlagChanged() {
    Task task = new Task(1, "Buy milk", false, NOW);

    Task done = task.withDone(true);

    assertThat(done.done()).isTrue();
    assertThat(done.id()).isEqualTo(task.id());
    assertThat(done.title()).isEqualTo(task.title());
    assertThat(done.createdAt()).isEqualTo(task.createdAt());
    assertThat(task.done()).isFalse();
  }
}
