package edu.ntnu.stud.view.components.prompt.prompts;

import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.math.*;
import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import edu.ntnu.stud.view.components.prompt.components.PromptField;
import edu.ntnu.stud.view.components.prompt.components.PromptFieldFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A predefined prompt dialog for inputting a fractal to the system. The dialog contains fields for
 * two vector coordinates, an optional name, and a set amount of transformations. These can be
 * filled with default values.
 *
 * @author Leif Mørstad
 * @version 2.0
 */
public class TransformationInputDialog extends PromptDialog {
  private final VBox transformationFields = new VBox();
  private final PromptField<?, String> nameField;
  private final PromptField<?, Vector> minCoordsField;
  private final PromptField<?, Vector> maxCoordsField;
  private final ArrayList<PromptField<?, AffineTransformation>> affineTransformationFields
      = new ArrayList<>();
  private final ArrayList<PromptField<?, ComplexNumber>> juliaTransformationFields
      = new ArrayList<>();

  /**
   * Creates a new dialog for adding a fractal.
   */
  public TransformationInputDialog(@NotNull String message) {
    this(message, false, null, null);
  }

  /**
   * Creates a new dialog for adding a fractal.
   */
  public TransformationInputDialog(
      @NotNull String message,
      boolean hideNameField,
      @Nullable Vector minCoords,
      @Nullable Vector maxCoords
  ) {
    super(true, message);
    setTitle(message);
    setExtraValidator(this::validate);

    VBox content = new VBox();

    nameField = hideNameField ? null : new PromptField<>(
        "Name",
        new TextField(),
        (field, error) -> field.getText()
    );
    minCoordsField = PromptFieldFactory.createVectorField(
        "Min coordinates (bottom left)",
        minCoords
    );
    maxCoordsField = PromptFieldFactory.createVectorField(
        "Max coordinates (top right)",
        maxCoords
    );
    if (!hideNameField) {
      content.getChildren().add(nameField);
    }
    content.getChildren().addAll(minCoordsField, maxCoordsField, transformationFields);

    setContent(content);

  }

  /**
   * Adds the given julia transformation field to the dialog.
   *
   * @param field the field to add
   */
  public void addJuliaTransformationField(PromptField<?, ComplexNumber> field) {
    juliaTransformationFields.add(field);
    transformationFields.getChildren().add(field);
  }

  /**
   * Adds a julia transformation field to the dialog.
   */
  public void addJuliaTransformationField() {
    addJuliaTransformationField(
        PromptFieldFactory.createComplexNumberField(
            "Julia Transformation " + (juliaTransformationFields.size() + 1),
            null
        )
    );
  }

  /**
   * Adds the given affine transformation field to the dialog.
   *
   * @param field the field to add
   */
  public void addAffineTransformationField(PromptField<?, AffineTransformation> field) {
    affineTransformationFields.add(field);
    transformationFields.getChildren().add(field);
  }

  /**
   * Adds an affine transformation field to the dialog.
   */
  public void addAffineTransformationField() {
    addAffineTransformationField(
        PromptFieldFactory.createAffineTransformationField(
            "Affine Transformation " + (affineTransformationFields.size() + 1),
            null
        )
    );
  }

  /**
   * Adds the amount of transformation fields to display.
   *
   * @param affineTransformationFields the amount of affine transformation fields to show
   * @param juliaTransformationFields  the amount of julia transformation fields to show
   */
  public void addTransformationFields(
      int affineTransformationFields,
      int juliaTransformationFields
  ) {
    for (int i = 0; i < affineTransformationFields; i++) {
      addAffineTransformationField();
    }
    for (int i = 0; i < juliaTransformationFields; i++) {
      addJuliaTransformationField();
    }
  }

  /**
   * Validates the dialog fields.
   *
   * @throws PromptValidationError if the fields are invalid
   */
  private void validate() throws PromptValidationError {
    if (nameField != null && nameField.getValue().isEmpty()) {
      throw new PromptValidationError("Name cannot be empty");
    }
  }

  /**
   * Waits for the result of the dialog and calls the consumer with the chaos game name and
   * description.
   *
   * @param consumer the consumer to call with the chaos game description
   */
  public boolean waitForResult(@NotNull ChaosGameDescriptionConsumer consumer) {
    boolean success = super.waitForResult();
    if (!success) {
      return false;
    }

    ArrayList<Transform2D> transformations = new ArrayList<>();
    for (var affineTransformationField : affineTransformationFields) {
      transformations.add(affineTransformationField.getCachedValue());
    }

    for (var juliaTransformation : juliaTransformationFields) {
      transformations.add(new JuliaTransformation(juliaTransformation.getCachedValue(), true));
      transformations.add(new JuliaTransformation(juliaTransformation.getCachedValue(), false));
    }

    ChaosGameDescription description = new ChaosGameDescription(
        minCoordsField.getCachedValue(),
        maxCoordsField.getCachedValue(),
        new TransformationGroup(transformations)
    );

    String fileName = nameField == null ? "empty" : nameField.getCachedValue() + ".json";

    consumer.accept(fileName, description);
    return true;
  }

  @Override
  public boolean waitForResult() {
    // To make sure the developer uses the correct method
    throw new UnsupportedOperationException("Use the other waitForResult method");
  }

  /**
   * A consumer for the chaos game description.
   */
  public interface ChaosGameDescriptionConsumer {
    void accept(@NotNull String name, @NotNull ChaosGameDescription description);
  }
}
