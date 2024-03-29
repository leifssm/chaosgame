package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An immutable two-dimensional vector with relevant methods.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class Vector {
  private final double x0;
  private final double x1;

  /**
   * Creates a new instance with the given x and y values.
   *
   * @param x0 the x value of the vector
   * @param x1 the y value of the vector
   */
  public Vector(double x0, double x1) {
    this.x0 = x0;
    this.x1 = x1;
  }

  /**
   * Returns the length of the vector.
   *
   * @return the hypotenuse of the x and the y of the vector
   */
  public double length() {
    return Math.sqrt(x0 * x0 + x1 * x1);
  }

  public double getX0() {
    return x0;
  }

  public double getX1() {
    return x1;
  }

  /**
   * Creates a new vector which represents the sum of this vector and the given vector.
   *
   * @param vector the vector to add to this vector
   * @return a new vector which is the sum of this vector and the given vector
   */
  public @NotNull Vector add(@NotNull Vector vector) {
    return new Vector(x0 + vector.getX0(), x1 + vector.getX1());
  }

  /**
   * Adds two vectors together and returns the result.
   *
   * @param a the first vector
   * @param b the second vector
   * @return a new vector which is the sum of the two vectors
   */
  @Contract("_, _ -> new")
  public static @NotNull Vector add(@NotNull Vector a, @NotNull Vector b) {
    return new Vector(a.getX0() + b.getX0(), a.getX1() + b.getX1());
  }

  /**
   * Creates a new vector which represents the difference between this vector and the given vector.
   *
   * @param vector the vector to subtract from this vector
   * @return a new vector which is the difference between this vector and the given vector
   */
  public @NotNull Vector subtract(@NotNull Vector vector) {
    return new Vector(x0 - vector.getX0(), x1 - vector.getX1());
  }

  /**
   * Subtracts two vectors and returns the result.
   *
   * @param a the first vector
   * @param b the second vector
   * @return a new vector which is the difference between the two vectors
   */
  @Contract("_, _ -> new")
  public static @NotNull Vector subtract(@NotNull Vector a, @NotNull Vector b) {
    return new Vector(a.getX0() - b.getX0(), a.getX1() - b.getX1());
  }

  /**
   * Creates a new vector which represents the vector scaled by the scalar.
   *
   * @param scalar the scalar to multiply the vector with
   * @return a new vector which is the vector scaled by the scalar
   */
  public @NotNull Vector multiply(double scalar) {
    return new Vector(x0 * scalar, x1 * scalar);
  }

  /**
   * Creates a new vector which represents the vector divided by the scalar.
   *
   * @param scalar the scalar to divide the vector by
   * @return a new vector which is the vector divided by the scalar
   */
  public @NotNull Vector divide(double scalar) {
    return new Vector(x0 / scalar, x1 / scalar);
  }

  /**
   * Calculates the dot product of two vectors and returns the result.
   *
   * @param a the first vector
   * @param b the second vector
   * @return the dot product of the two vectors
   */
  public static double dot(@NotNull Vector a, @NotNull Vector b) {
    return a.getX0() * b.getX0() + a.getX1() * b.getX1();
  }

  /**
   * Returns the dot product of this vector and the given vector.
   *
   * @param vector the vector to calculate the dot product with
   * @return the dot product of this vector and the given vector
   */
  public double dot(@NotNull Vector vector) {
    return Vector.dot(this, vector);
  }

  /**
   * Calculates the angle between two vectors and returns the result.
   *
   * @param a the first vector
   * @param b the second vector
   * @return the angle between the two vectors
   */
  public static double angle(@NotNull Vector a, @NotNull Vector b) {
    return Math.acos(
        Vector.dot(a, b) / (a.length() * b.length())
    );
  }

  /**
   * Returns the angle between this vector and the given vector.
   *
   * @param vector the vector to calculate the angle with
   * @return the angle between this vector and the given vector
   */
  public double angle(@NotNull Vector vector) {
    return Vector.angle(this, vector);
  }
}
