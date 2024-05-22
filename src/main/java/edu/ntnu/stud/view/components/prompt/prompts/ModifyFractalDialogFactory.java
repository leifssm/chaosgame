package edu.ntnu.stud.view.components.prompt.prompts;

import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.JuliaTransformation;
import edu.ntnu.stud.model.math.Transform2D;
import edu.ntnu.stud.view.components.prompt.components.PromptFieldFactory;
import org.jetbrains.annotations.NotNull;

/**
 * A predefined prompt dialog for adding a fractal to the system. The dialog contains fields for two
 * vector coordinates, a name, and a set amount of affine transformations.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ModifyFractalDialogFactory {

  public static @NotNull TransformationInputDialog create(
      boolean hideNameField,
      @NotNull ChaosGameDescription description
  ) {
    int affineTransformationAmount = 0;
    int juliaTransformationAmount = 0;

    var dialog = new TransformationInputDialog(
        "Modify fractal",
        hideNameField,
        description.minCoords(),
        description.maxCoords()
    );

    for (Transform2D transformation : description.transformations().getTransformations()) {
      if (transformation instanceof AffineTransformation) {
        affineTransformationAmount++;
        var field = PromptFieldFactory.createAffineTransformationField(
            "Affine transformation " + affineTransformationAmount,
            (AffineTransformation) transformation
        );
        dialog.addAffineTransformationField(field);
        continue;
      }
      if (transformation instanceof JuliaTransformation) {
        if (((JuliaTransformation) transformation).getSign() == -1) {
          continue;
        }
        juliaTransformationAmount++;
        var field = PromptFieldFactory.createComplexNumberField(
            "Julia transformation " + juliaTransformationAmount,
            ((JuliaTransformation) transformation).getComplexNumber()
        );
        dialog.addJuliaTransformationField(field);
      }
    }
    return dialog;
  }
}
