package edu.ntnu.stud.model;

import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class TestTemplate {
  @Nested
  class PositiveTests {
    @Test
    @DisplayName("")
    void constructor() {
    }
  }

  @Nested
  class NegativeTests {
    @Test
    @DisplayName("")
    void constructor() {
    }
  }
}