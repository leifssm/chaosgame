package edu.ntnu.stud.model.math;

import edu.ntnu.stud.utils.ToStringBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * An immutable two-dimensional vector with relevant methods.
 *
 * @author Leif Mørstad
 * @version 1.4
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
    checkNaN();
  }

  /**
   * Creates a new instance from the given array of values.
   *
   * @param values an array with exactly two numerical values;
   * @throws IllegalArgumentException if the array does not have exactly two values
   */
  public Vector(double[] values) throws IllegalArgumentException {
    if (values.length != 2) {
      throw new IllegalArgumentException("Given array must have exactly two values");
    }
    this.x0 = values[0];
    this.x1 = values[1];
    checkNaN();
  }

  private void checkNaN() {
    if (Double.isNaN(x0) || Double.isNaN(x1)) {
      throw new IllegalArgumentException("Vector contains NaN values");
    }
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
    return Vector.add(this, vector);
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
    return Vector.subtract(this, vector);
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
    if (scalar == 0) {
      throw new IllegalArgumentException("Cannot divide by zero");
    }
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

  /**
   * Returns whether the given object is equal to this vector.
   *
   * @param o the object to compare with
   * @return whether the given object is equal to this vector
   */
  @Override
  public boolean equals(Object o) {
    // Generated by IntelliJ IDEA
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vector vector = (Vector) o;
    return Double.compare(x0, vector.x0) == 0 && Double.compare(x1, vector.x1) == 0;
  }

  @Override
  public int hashCode() {
    // Generated by IntelliJ IDEA
    return Objects.hash(x0, x1);
  }

  /**
   * Returns a string representation of the vector.
   *
   * @return a string representation of the vector
   */
  public @NotNull String asString() {
    return "(" + x0 + ", " + x1 + ")";
  }

  /**
   * Returns a string representation of the vector.
   *
   * @return a string representation of the vector
   */
  public String toString() {
    return new ToStringBuilder(this)
        .field("x0", x0)
        .field("x1", x1)
        .build();
  }
}
