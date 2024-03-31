package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an affine transformation in two dimensions. The transformation is a multiplication
 * followed by a translation. Identical to <pre>u = Ax + b</pre>
 *
 * @version 1.3
 * @author Leif Mørstad
 */
public class AffineTransformation implements Transform2D {
  /**
   * The matrix which the input vector is multiplied by.
   */
  private final @NotNull SimpleMatrix matrix;

  /**
   * The translation which is added to the result of the matrix multiplication.
   */
  private final @NotNull Vector translation;

  /**
   * Creates a new instance with the given matrix and translation.
   *
   * @param matrix the matrix of the transformation
   * @param translation the translation of the transformation
   * @throws IllegalArgumentException if any of the given values are null
   */
  public AffineTransformation(@NotNull SimpleMatrix matrix, @NotNull Vector translation) {
    this.matrix = matrix;
    this.translation = translation;
  }

  /**
   * Creates a new instance with the given matrix and translation.
   *
   * @param values an array with exactly six numerical values representing in order: a00, a01, a10,
   *               a11, vx, and vy
   * @throws IllegalArgumentException if the array does not have exactly six values, or if passed a
   *                                  null array
   */
  public AffineTransformation(double @NotNull ... values) throws IllegalArgumentException {
    if (values.length != 6) {
      throw new IllegalArgumentException("Given array must have exactly six values");
    }
    this.matrix = new SimpleMatrix(values[0], values[1], values[2], values[3]);
    this.translation = new Vector(values[4], values[5]);
  }

  /**
   * Creates a new instance with no translation, using an identity vector and a zero vector.
   */
  public AffineTransformation() {
    this(new SimpleMatrix(1, 0, 0, 1), new Vector(0, 0));
  }

  /**
   * Transforms the given vector using the matrix and translation of this transformation.
   *
   * @param vector the vector to transform
   * @return the transformed vector
   * @throws IllegalArgumentException if the given vector is null
   */
  public @NotNull Vector transform(@NotNull Vector vector) {
    return matrix
        .transform(vector)
        .add(translation);
  }
}
