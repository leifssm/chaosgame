package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * A simple two-by-two matrix.
 *
 * @version 1.1
 * @author Leif Mørstad
 */
public class SimpleMatrix {
  private final double[][] values;

  /**
   * Creates a new two-by-two matrix with the given values.
   *
   * @param a00 the value at position (0, 0)
   * @param a01 the value at position (0, 1)
   * @param a10 the value at position (1, 0)
   * @param a11 the value at position (1, 1)
   */
  public SimpleMatrix(double a00, double a01, double a10, double a11) {
    values = new double[][]{ {a00, a01}, {a10, a11} };
  }

  /**
   * Returns the value at the given position in the matrix.
   *
   * @param x the x-coordinate of the value, should be either 0 or 1
   * @param y the y-coordinate of the value, should be either 0 or 1
   * @return the value at the given position in the matrix
   */
  public double get(int x, int y) {
    return values[y][x];
  }

  /**
   * Multiplies this matrix with the given vector, and returns the resulting vector.
   *
   * @param vector the vector to multiply this matrix with
   * @return the resulting vector after the multiplication
   */
  public Vector multiply(@NotNull Vector vector) {
    return new Vector(
        get(0, 0) * vector.getX0() + get(0, 1) * vector.getX1(),
        get(1, 0) * vector.getX0() + get(1, 1) * vector.getX1()
    );
  }
}
