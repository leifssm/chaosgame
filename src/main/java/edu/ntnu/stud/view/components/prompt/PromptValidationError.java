package edu.ntnu.stud.view.components.prompt;

/**
 * An exception thrown when a prompt field fails validation.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class PromptValidationError extends Exception {
  /**
   * Creates a new prompt validation error with the given message.
   *
   * @param message the error message
   */
  public PromptValidationError(String message) {
    super(message);
  }
}
