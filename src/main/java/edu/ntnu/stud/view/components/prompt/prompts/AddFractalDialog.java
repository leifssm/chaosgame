package edu.ntnu.stud.view.components.prompt.prompts;

import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import edu.ntnu.stud.model.math.*;
import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import edu.ntnu.stud.view.components.prompt.components.PromptField;
import edu.ntnu.stud.view.components.prompt.components.PromptFieldFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AddFractalDialog extends PromptDialog {
  private final VBox transformationFields = new VBox();
  private final PromptField<?, String> nameField;
  private final PromptField<?, Vector> minCoordsField;
  private final PromptField<?, Vector> maxCoordsField;
  private final ArrayList<PromptField<?, AffineTransformation>> affineTransformationFields
      = new ArrayList<>();
  private final ArrayList<PromptField<?, ComplexNumber>> juliaTransformationFields
      = new ArrayList<>();

  public AddFractalDialog() {
    super(true, "Add fractal");
    setTitle("Add fractal");
    setExtraValidator(this::validate);

    VBox content = new VBox();

    nameField = new PromptField<>(
        "Name",
        new TextField(),
        (field, error) -> field.getText()
    );
    minCoordsField = PromptFieldFactory.createVectorField("Min coordinates (top left)");
    maxCoordsField = PromptFieldFactory.createVectorField("Max coordinates (bottom right)");
    content.getChildren().addAll(nameField, minCoordsField, maxCoordsField, transformationFields);

    setContent(content);

  }

  public void setTransformationFields(
      int affineTransformationFields,
      int juliaTransformationFields
  ) {
    System.out.println("Setting transformation fields" + affineTransformationFields + " " + juliaTransformationFields);
    transformationFields.getChildren().clear();
    this.juliaTransformationFields.clear();
    this.affineTransformationFields.clear();

    for (int i = 0; i < affineTransformationFields; i++) {
      this.affineTransformationFields.add(
          PromptFieldFactory.createAffineTransformationField(
              "Affine Transformation " + (i + 1)
          )
      );
    }

    for (int i = 0; i < juliaTransformationFields; i++) {
      this.juliaTransformationFields.add(
          PromptFieldFactory.createComplexNumberField(
              "Julia Transformation " + (i + 1)
          )
      );
    }
    transformationFields.getChildren().addAll(this.affineTransformationFields);
    transformationFields.getChildren().addAll(this.juliaTransformationFields);
  }

  private void validate() throws PromptValidationError {
    if (nameField != null && nameField.getValue().isEmpty()) {
      throw new PromptValidationError("Name cannot be empty");
    }
  }

  @Override
  public boolean waitForResult() {
    boolean success = super.waitForResult();
    if (!success) {
      return false;
    }

    ArrayList<Transform2D> transformations = new ArrayList<>();
    for (var affineTransformationField : affineTransformationFields) {
      transformations.add(affineTransformationField.getCachedValue());
    }

    for (var juliaTransformation : juliaTransformationFields) {
      ComplexNumber complexNumber = juliaTransformation.getCachedValue();
      transformations.add(new JuliaTransformation(complexNumber, true));
      transformations.add(new JuliaTransformation(complexNumber, false));
    }


    ChaosGameDescription description = new ChaosGameDescription(
        minCoordsField.getCachedValue(),
        maxCoordsField.getCachedValue(),
        new TransformationGroup(transformations)
    );
    ChaosGameFileHandler.writeToFile(
        nameField.getCachedValue() + ".json",
        description
    );
    return true;
  }
}
