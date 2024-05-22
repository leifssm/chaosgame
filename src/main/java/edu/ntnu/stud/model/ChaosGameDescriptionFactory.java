package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.TransformationGroup;
import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * Factory for creating predefined chaos game descriptions.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ChaosGameDescriptionFactory {
  /**
   * Creates a new chaos game description for the Sierpinski triangle.
   *
   * @return a sierpinski triangle chaos game description
   */
  public static @NotNull ChaosGameDescription createSierpinski() {
    return new ChaosGameDescription(
        new Vector(-0.2, -0.2),
        new Vector(1.2, 1.2),
        new TransformationGroup(
            new AffineTransformation(0.5, 0.0, 0.0, 0.5, 0.0, 0.0),
            new AffineTransformation(0.5, 0.0, 0.0, 0.5, 0.5, 0.0),
            new AffineTransformation(0.5, 0.0, 0.0, 0.5, 0.25, 0.5)
        )
    );
  }
}
