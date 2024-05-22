package edu.ntnu.stud.view.components.prompt.components;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A class for handling the error stack in nested prompt fields for improved error messages in
 * prompts.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class PromptErrorStack {
  private final ArrayList<String> stack = new ArrayList<>();

  /**
   * Creates a new instance with the given field name.
   *
   * @param field the name of the field
   */
  public PromptErrorStack(@NotNull String field) {
    stack.add(field);
  }

  /**
   * Adds a field to the stack.
   *
   * @param field the name of the field
   */
  public void add(@NotNull String field) {
    stack.add(field);
  }

  /**
   * Returns a user-friendly string representation of the error stack.
   *
   * @return the error stack
   */
  public @NotNull String compile() {
    return "Error in " + String.join(" -> ", stack.reversed()) + ": ";
  }
}
