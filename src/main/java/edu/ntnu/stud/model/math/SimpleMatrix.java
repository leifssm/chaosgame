package edu.ntnu.stud.model.math;

import edu.ntnu.stud.utils.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A simple two-by-two matrix.
 *
 * @author Leif Mørstad
 * @version 2.1
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

  /**
   * Returns A^-1 if possible, throws if the matrix is not invertible.
   *
   * @return A^-1
   */
  public @NotNull SimpleMatrix invert() {
    double determinant = a00 * a11 - a01 * a10;
    if (determinant == 0) {
      throw new IllegalArgumentException("Matrix is not invertible");
    }
    return new SimpleMatrix(
        a11 / determinant,
        -a01 / determinant,
        -a10 / determinant,
        a00 / determinant
    );
  }

  /**
   * Returns the matrix of this transformation as an easily loggable string.
   *
   * @return the matrix as a string
   */
  public @NotNull String asSimpleString() {
    int firstSegmentLength = Math.max(String.valueOf(a00).length(), String.valueOf(a01).length());
    int secondSegmentLength = Math.max(String.valueOf(a10).length(), String.valueOf(a11).length());
    return "|%s, %s|\n|%s, %s|".formatted(
        StringUtils.padLeft(a00 + "", firstSegmentLength),
        StringUtils.padLeft(a01 + "", secondSegmentLength),
        StringUtils.padLeft(a10 + "", firstSegmentLength),
        StringUtils.padLeft(a11 + "", secondSegmentLength)
    );
  }
}
