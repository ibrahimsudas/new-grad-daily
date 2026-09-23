package dev.taskflow;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void printsVersionFlag() {
    assertThat(Main.run(new String[] {"--version"})).isEqualTo(Main.VERSION);
  }

  @Test
  void printsHelpTextWithNoArgs() {
    assertThat(Main.run(new String[] {})).contains("taskflow");
  }
}
