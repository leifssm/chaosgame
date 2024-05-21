package edu.ntnu.stud.view.components.prompt.components;

import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.ComplexNumber;
import edu.ntnu.stud.model.math.Vector;
import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.NotNull;

public class PromptFieldFactory {
  public static @NotNull PromptField<?, @NotNull Integer> createIntegerField(
      @NotNull String prompt
  ) {
    return new PromptField<>(prompt, new TextField(), (field, errorStack) -> {
      try {
        return Integer.parseInt(field.getText());
      } catch (NumberFormatException | NullPointerException e) {
        throw new PromptValidationError(
            errorStack
                + "Could not interpret value '"
                + (field.getText().isEmpty() ? "<empty>" : field.getText())
                + "'."
        );
      }
    });
  }

  public static @NotNull PromptField<?, @NotNull Double> createDoubleField(
      @NotNull String prompt
  ) {
    return new PromptField<>(prompt, new TextField(), (field, errorStack) -> {
      try {
        return Double.parseDouble(field.getText());
      } catch (NumberFormatException | NullPointerException e) {
        throw new PromptValidationError(
            errorStack
                + "Could not interpret value '"
                + (field.getText().isEmpty() ? "<empty>" : field.getText())
                + "'."
        );
      }
    });
  }

  public static @NotNull PromptField<?, @NotNull Vector> createVectorField(
      @NotNull String prompt
  ) {
    var x0 = createDoubleField("x");
    x0.getErrorStack().add(prompt);
    var x1 = createDoubleField("y");
    x1.getErrorStack().add(prompt);

    var content = new HBox(x0, x1);

    return new PromptField<>(prompt, content, (field, errorStack) -> {
      try {
        return new Vector(x0.getValue(), x1.getValue());
      } catch (IllegalArgumentException e) {
        throw new PromptValidationError(errorStack + e.getMessage());
      }
    });
  }

  public static @NotNull PromptField<?, @NotNull ComplexNumber> createComplexNumberField(
      @NotNull String prompt
  ) {
    var x0 = createDoubleField("Real");
    x0.getErrorStack().add(prompt);
    var x1 = createDoubleField("Complex");
    x1.getErrorStack().add(prompt);

    var content = new HBox(x0, x1);

    return new PromptField<>(prompt, content, (field, errorStack) -> {
      try {
        return new ComplexNumber(x0.getValue(), x1.getValue());
      } catch (IllegalArgumentException e) {
        throw new PromptValidationError(errorStack + e.getMessage());
      }
    });
  }

  public static @NotNull PromptField<?, @NotNull AffineTransformation> createAffineTransformationField(
      @NotNull String prompt
  ) {
    var a00 = createDoubleField("a00");
    a00.getErrorStack().add(prompt);
    var a01 = createDoubleField("a01");
    a01.getErrorStack().add(prompt);
    var a10 = createDoubleField("a10");
    a10.getErrorStack().add(prompt);
    var a11 = createDoubleField("a11");
    a11.getErrorStack().add(prompt);
    var x0 = createDoubleField("x");
    x0.getErrorStack().add(prompt);
    var x1 = createDoubleField("y");
    x1.getErrorStack().add(prompt);

    var content = new GridPane();
    content.addRow(0, a00, a01, x0);
    content.addRow(1, a10, a11, x1);

    return new PromptField<>(prompt, content, (field, errorStack) -> {
      try {
        return new AffineTransformation(
            a00.getValue(),
            a01.getValue(),
            a10.getValue(),
            a11.getValue(),
            x0.getValue(),
            x1.getValue()
        );
      } catch (IllegalArgumentException e) {
        throw new PromptValidationError(errorStack + e.getMessage());
      }
    });
  }
}
