package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * A simple two-by-two matrix.
 *
 * @version 2.0
 * @author Leif Mørstad
 */
public class SimpleMatrix implements Transform2D {
  /**
   * The top left corner of the matrix. Cannot be {@link Double#NaN}
   */
  private final double a00;
  /**
   * The top right corner of the matrix. Cannot be {@link Double#NaN}
   */
  private final double a01;
  /**
   * The bottom left corner of the matrix. Cannot be {@link Double#NaN}
   */
  private final double a10;
  /**
   * The bottom right corner of the matrix. Cannot be {@link Double#NaN}
   */
  private final double a11;

  /**
   * Creates a new two-by-two matrix with the given values.
   *
   * @param a00 the value at position (0, 0)
   * @param a01 the value at position (0, 1)
   * @param a10 the value at position (1, 0)
   * @param a11 the value at position (1, 1)
   * @throws IllegalArgumentException if any of the values are {@link Double#NaN}
   */
  public SimpleMatrix(
      double a00,
      double a01,
      double a10,
      double a11
  ) throws IllegalArgumentException {
    if (Double.isNaN(a00) || Double.isNaN(a01) || Double.isNaN(a10) || Double.isNaN(a11)) {
      throw new IllegalArgumentException("Matrix values cannot be NaN");
    }
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * Returns the top left corner of the matrix. Is not {@link Double#NaN}
   *
   * @return the top left corner of the matrix
   */
  double getA00() {
    return a00;
  }

  /**
   * Returns the top right corner of the matrix. Is not {@link Double#NaN}
   *
   * @return the top right corner of the matrix
   */
  double getA01() {
    return a01;
  }

  /**
   * Returns the bottom left corner of the matrix. Is not {@link Double#NaN}
   *
   * @return the bottom left corner of the matrix
   */
  double getA10() {
    return a10;
  }

  /**
   * Returns the bottom right corner of the matrix. Is not {@link Double#NaN}
   *
   * @return the bottom right corner of the matrix
   */
  double getA11() {
    return a11;
  }

  /**
   * Multiplies this matrix with the given vector, and returns the resulting vector.
   *
   * @param vector the vector to multiply this matrix with
   * @return the resulting vector after the multiplication
   * @throws IllegalArgumentException if the given vector is null
   */
  public @NotNull Vector transform(@NotNull Vector vector) throws IllegalArgumentException {
    return new Vector(
        a00 * vector.getX0() + a01 * vector.getX1(),
        a10 * vector.getX0() + a11 * vector.getX1()
    );
  }
}
