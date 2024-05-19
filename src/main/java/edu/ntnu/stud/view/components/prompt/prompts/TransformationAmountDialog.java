package edu.ntnu.stud.view.components.prompt.prompts;

import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import edu.ntnu.stud.view.components.prompt.components.FieldFactory;
import edu.ntnu.stud.view.components.prompt.components.PromptField;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class TransformationAmountDialog extends PromptDialog {
  private final PromptField<?, @NotNull Integer> affineTransformationAmountField;
  private final PromptField<?, @NotNull Integer> juliaTransformationAmountField;

  public TransformationAmountDialog() {
    super(true, "Continue");
    setTitle("Add fractal");
    VBox content = new VBox();

    affineTransformationAmountField = FieldFactory.createIntegerField(
        "Amount of affine transformations"
    );
    juliaTransformationAmountField = FieldFactory.createIntegerField(
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
