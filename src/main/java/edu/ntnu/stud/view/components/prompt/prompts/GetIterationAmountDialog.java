package edu.ntnu.stud.view.components.prompt.prompts;

import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import edu.ntnu.stud.view.components.prompt.components.PromptField;
import edu.ntnu.stud.view.components.prompt.components.PromptFieldFactory;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A dialog for asking the user for the amount of iterations to perform on a fractal.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class GetIterationAmountDialog extends PromptDialog {
  private final PromptField<?, @NotNull Integer> iterationAmountField;
  private final Consumer<Integer> onResult;

  /**
   * Creates a new dialog for asking the user for the amount of iterations to perform.
   *
   * @param onResult the consumer to call if the user has entered a valid amount
   */
  public GetIterationAmountDialog(Consumer<Integer> onResult) {
    super(true, "Continue");
    setTitle("Add fractal");
    VBox content = new VBox();

    iterationAmountField = PromptFieldFactory.createIntegerField(
        "Amount of iterations to perform",
        null
    );

    setExtraValidator(() -> {
      if (iterationAmountField.getValue() < 0) {
        throw new PromptValidationError("The amount of iterations must be a positive number");
      }
    });

    content.getChildren().add(iterationAmountField);

    this.onResult = onResult;
    setContent(content);
  }

  @Override
  public boolean waitForResult() {
    boolean success = super.waitForResult();
    if (success) {
      onResult.accept(iterationAmountField.getCachedValue());
      return true;
    }
    return false;
  }
}
