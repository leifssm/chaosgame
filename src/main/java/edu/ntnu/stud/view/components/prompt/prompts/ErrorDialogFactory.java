package edu.ntnu.stud.view.components.prompt.prompts;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class ErrorDialogFactory {
  public static PromptDialog create(@NotNull String message) {
    return new PromptDialog.PromptDialogBuilder()
        .setTitle("Error encountered")
        .setContent(new Label(message))
        .build();
  }
}
