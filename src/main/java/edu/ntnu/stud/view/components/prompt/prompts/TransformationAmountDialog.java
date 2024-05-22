package edu.ntnu.stud.view.components.prompt.prompts;

import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import edu.ntnu.stud.view.components.prompt.components.PromptField;
import edu.ntnu.stud.view.components.prompt.components.PromptFieldFactory;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * A dialog for asking the user for the amount of affine and julia transformations they want to add
 * to a fractal.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class TransformationAmountDialog extends PromptDialog {
  private final PromptField<?, @NotNull Integer> affineTransformationAmountField;
  private final PromptField<?, @NotNull Integer> juliaTransformationAmountField;

  /**
   * Creates a new dialog for asking the user for the amount of affine and julia transformations
   * they want
   */
  public TransformationAmountDialog() {
    super(true, "Continue");
    setTitle("Add fractal");
    VBox content = new VBox();

    affineTransformationAmountField = PromptFieldFactory.createIntegerField(
        "Amount of affine transformations"
    );
    juliaTransformationAmountField = PromptFieldFactory.createIntegerField(
        "Amount of julia transformations"
    );

    setExtraValidator(() -> {
      if (affineTransformationAmountField.getValue() < 0) {
        throw new PromptValidationError("Affine transformation cannot be less than 0");
      }
      if (juliaTransformationAmountField.getValue() < 0) {
        throw new PromptValidationError("Julia transformation cannot be less than 0");
      }
    });

    content.getChildren().addAll(affineTransformationAmountField, juliaTransformationAmountField);

    setContent(content);
  }

  @Override
  public boolean waitForResult() {
    boolean success = super.waitForResult();
    if (success) {
      var dialog = new AddFractalDialog();
      dialog.setTransformationFields(
          affineTransformationAmountField.getCachedValue(),
          juliaTransformationAmountField.getCachedValue()
      );
      return dialog.waitForResult();
    }
    return false;
  }
}
