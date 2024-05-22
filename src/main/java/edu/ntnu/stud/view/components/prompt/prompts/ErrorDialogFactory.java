package edu.ntnu.stud.view.components.prompt.prompts;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

/**
 * A factory class for creating simple error dialogs.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ErrorDialogFactory {
  /**
   * Creates a simple error dialog with the given message.
   *
   * @param message the message to display
   * @return the created dialog
   */
  public static @NotNull PromptDialog create(@NotNull String message) {
    return new PromptDialog.PromptDialogBuilder()
        .setTitle("Error encountered")
        .setContent(new Label(message))
        .build();
  }

  /**
   * Creates and shows a simple error dialog with the given message.
   *
   * @param message the message to display
   */
  public static void show(@NotNull String message) {
    create(message).showAndWait();
  }
}
