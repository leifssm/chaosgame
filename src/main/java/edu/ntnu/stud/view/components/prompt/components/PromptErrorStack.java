package edu.ntnu.stud.view.components.prompt.components;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PromptErrorStack {
  private final ArrayList<String> stack = new ArrayList<>();

  public PromptErrorStack(@NotNull String field) {
    stack.add(field);
  }

  public void add(@NotNull String field) {
    stack.add(field);
  }

  public @NotNull String compile() {
    return "Error in " + String.join(" -> ", stack.reversed()) + ": ";
  }
}
