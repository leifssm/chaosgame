package edu.ntnu.stud.view.components.prompt.components;

import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.ComplexNumber;
import edu.ntnu.stud.model.math.SimpleMatrix;
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
    var x = createDoubleField("x");
    x.getErrorStack().add(prompt);
    var y = createDoubleField("y");
    y.getErrorStack().add(prompt);

    var content = new HBox(x, y);

    return new PromptField<>(prompt, content, (field, errorStack) -> {
      try {
        return new Vector(x.getValue(), y.getValue());
      } catch (IllegalArgumentException e) {
        throw new PromptValidationError(errorStack + e.getMessage());
      }
    });
  }

  public static @NotNull PromptField<?, @NotNull ComplexNumber> createComplexNumberField(
      @NotNull String prompt
  ) {
    var x = createDoubleField("Real");
    x.getErrorStack().add(prompt);
    var y = createDoubleField("Complex");
    y.getErrorStack().add(prompt);

    var content = new HBox(x, y);

    return new PromptField<>(prompt, content, (field, errorStack) -> {
      try {
        return new ComplexNumber(x.getValue(), y.getValue());
      } catch (IllegalArgumentException e) {
        throw new PromptValidationError(errorStack + e.getMessage());
      }
    });
  }

  public static @NotNull PromptField<?, @NotNull SimpleMatrix> createMatrixField(
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

    var content = new GridPane();
    content.addRow(0, a00, a01);
    content.addRow(1, a10, a11);

    return new PromptField<>(prompt, content, (field, errorStack) -> {
      try {
        return new SimpleMatrix(a00.getValue(), a01.getValue(), a10.getValue(), a11.getValue());
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
    var x = createDoubleField("x");
    x.getErrorStack().add(prompt);
    var y = createDoubleField("y");
    y.getErrorStack().add(prompt);

    var content = new GridPane();
    content.addRow(0, a00, a01, x);
    content.addRow(1, a10, a11, y);

    return new PromptField<>(prompt, content, (field, errorStack) -> {
      try {
        return new AffineTransformation(
            a00.getValue(),
            a01.getValue(),
            a10.getValue(),
            a11.getValue(),
            x.getValue(),
            y.getValue()
        );
      } catch (IllegalArgumentException e) {
        throw new PromptValidationError(errorStack + e.getMessage());
      }
    });
  }
}
