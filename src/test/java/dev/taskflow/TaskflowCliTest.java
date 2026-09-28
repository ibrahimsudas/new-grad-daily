package dev.taskflow;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class TaskflowCliTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  private final TaskStore store =
      new TaskStore(Clock.fixed(Instant.parse("2024-01-01T00:00:00Z"), ZoneOffset.UTC));

  @BeforeEach
  void redirectStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  private int execute(String... args) {
    return new CommandLine(new TaskflowCli(store)).execute(args);
  }

  @Test
  void printsVersion() {
    int exitCode = execute("--version");

    assertThat(exitCode).isZero();
    assertThat(outContent.toString()).contains(Main.VERSION);
  }

  @Test
  void printsUsageWithNoArgs() {
    int exitCode = execute();

    assertThat(exitCode).isZero();
    assertThat(outContent.toString()).contains("taskflow");
  }

  @Test
  void addsATask() {
    int exitCode = execute("add", "Buy", "milk");

    assertThat(exitCode).isZero();
    assertThat(outContent.toString()).contains("Added task 1: Buy milk");
    assertThat(store.list()).extracting(Task::title).containsExactly("Buy milk");
  }

  @Test
  void listsNoTasks() {
    int exitCode = execute("list");

    assertThat(exitCode).isZero();
    assertThat(outContent.toString()).contains("No tasks yet.");
  }

  @Test
  void listsTasksInAddedOrderWithDoneMarkers() {
    store.add("Buy milk");
    Task walkTheDog = store.add("Walk the dog");
    store.complete(walkTheDog.id());

    int exitCode = execute("list");

    assertThat(exitCode).isZero();
    assertThat(outContent.toString()).contains("[ ] 1  Buy milk").contains("[x] 2  Walk the dog");
  }

  @Test
  void marksATaskDone() {
    store.add("Buy milk");

    int exitCode = execute("done", "1");

    assertThat(exitCode).isZero();
    assertThat(outContent.toString()).contains("Completed task 1: Buy milk");
    assertThat(store.get(1).done()).isTrue();
  }

  @Test
  void doneWithUnknownIdIsAUserError() {
    int exitCode = execute("done", "99");

    assertThat(exitCode).isEqualTo(1);
  }

  @Test
  void doneWithNonNumericIdIsAUsageError() {
    int exitCode = execute("done", "abc");

    assertThat(exitCode).isEqualTo(2);
    assertThat(errContent.toString()).isNotBlank();
  }

  @Test
  void removesATask() {
    store.add("Buy milk");

    int exitCode = execute("rm", "1");

    assertThat(exitCode).isZero();
    assertThat(outContent.toString()).contains("Removed task 1: Buy milk");
    assertThat(store.list()).isEmpty();
  }

  @Test
  void rmWithUnknownIdIsAUserError() {
    int exitCode = execute("rm", "99");

    assertThat(exitCode).isEqualTo(1);
  }

  @Test
  void rmWithNonNumericIdIsAUsageError() {
    int exitCode = execute("rm", "abc");

    assertThat(exitCode).isEqualTo(2);
  }
}
