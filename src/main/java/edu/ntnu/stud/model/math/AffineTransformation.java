package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an affine transformation in two dimensions. The transformation is a multiplication
 * followed by a translation.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class AffineTransformation {
  private final SimpleMatrix matrix;
  private final Vector translation;

  /**
   * Creates a new instance with the given matrix and translation.
   *
   * @param matrix the matrix of the transformation
   * @param translation the translation of the transformation
   */
  public AffineTransformation(@NotNull SimpleMatrix matrix, @NotNull Vector translation) {
    this.matrix = matrix;
    this.translation = translation;
  }

  /**
   * Transforms the given vector using the matrix and translation of this transformation.
   *
   * @param vector the vector to transform
   * @return the transformed vector
   */
  public @NotNull Vector transform(@NotNull Vector vector) {
    return matrix
        .multiply(vector)
        .add(translation);
  }
}
