package edu.ntnu.stud.model.math;

import edu.ntnu.stud.utils.ToStringBuilder;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An immutable two-dimensional vector with relevant methods.
 *
 * @author Leif Mørstad
 * @version 1.4
 */
public class Vector {
  /**
   * The first value of the vector. Cannot be {@link Double#NaN}
   */
  private final double x0;
  
  /**
   * The second value of the vector. Cannot be {@link Double#NaN}
   */
  private final double x1;

  /**
   * Creates a new instance with the given x0 and x1 values.
   *
   * @param x0 the first value of the vector
   * @param x1 the second value of the vector
   * @throws IllegalArgumentException if any of the values are {@link Double#NaN}
   */
  public Vector(double x0, double x1) throws IllegalArgumentException {
    this.x0 = x0;
    this.x1 = x1;
    checkNaN();
  }

  /**
   * Creates a new instance from the given array of values.
   *
   * @param values an array with exactly two numerical values
   * @throws IllegalArgumentException if the array does not have exactly two values or if any of the
   *                                  values are {@link Double#NaN}
   */
  public Vector(double @NotNull [] values) throws IllegalArgumentException {
    // Annotations are ignored when running mvn package
    if (values == null) {
      throw new IllegalArgumentException("The array of values cannot be null");
    }
    if (values.length != 2) {
      throw new IllegalArgumentException("Given array must have exactly two values");
    }
    this.x0 = values[0];
    this.x1 = values[1];
    checkNaN();
  }

  /**
   * Checks if the vector contains any NaN values, and throws if it does.
   *
   * @throws IllegalArgumentException if the vector contains any NaN values
   */
  private void checkNaN() throws IllegalArgumentException {
    if (Double.isNaN(x0) || Double.isNaN(x1)) {
      throw new IllegalArgumentException("Vector contains NaN values");
    }
  }

  /**
   * Returns the length of the vector.
   *
   * @return the hypotenuse of the x0 and the x1 of the vector
   */
  public double length() {
    return Math.sqrt(x0 * x0 + x1 * x1);
  }

  /**
   * Returns the first value of the vector.
   *
   * @return the first value of the vector
   * @see Vector#x0
   */
  public double getX0() {
    return x0;
  }

  /**
   * Returns the second value of the vector.
   *
   * @return the second value of the vector
   * @see Vector#x1
   */
  public double getX1() {
    return x1;
  }

  /**
   * Creates a new vector which represents the sum of this vector and the given vector.
   *
   * @param vector the vector to add to this vector
   * @return a new vector which is the sum of this vector and the given vector
   * @throws IllegalArgumentException if the given vector is null
   */
  public @NotNull Vector add(@NotNull Vector vector) throws IllegalArgumentException {
    return Vector.add(this, vector);
  }

  /**
   * Adds two vectors together and returns the result.
   *
   * @param a the first vector
   * @param b the second vector
   * @return a new vector which is the sum of the two vectors
   * @throws IllegalArgumentException if either of the given vectors are null
   */
  @Contract("_, _ -> new")
  public static @NotNull Vector add(
      @NotNull Vector a,
      @NotNull Vector b
  ) throws IllegalArgumentException {
    return new Vector(a.getX0() + b.getX0(), a.getX1() + b.getX1());
  }

  /**
   * Creates a new vector which represents the difference between this vector and the given vector.
   *
   * @param vector the vector to subtract from this vector
   * @return a new vector which is the difference between this vector and the given vector
   * @throws IllegalArgumentException if the given vector is null
   */
  public @NotNull Vector subtract(@NotNull Vector vector) throws IllegalArgumentException {
    return Vector.subtract(this, vector);
  }

  /**
   * Subtracts two vectors and returns the result.
   *
   * @param a the first vector
   * @param b the second vector
   * @return a new vector which is the difference between the two vectors
   * @throws IllegalArgumentException if either of the given vectors are null
   */
  @Contract("_, _ -> new")
  public static @NotNull Vector subtract(
      @NotNull Vector a,
      @NotNull Vector b
  ) throws IllegalArgumentException {
    return new Vector(a.getX0() - b.getX0(), a.getX1() - b.getX1());
  }

  /**
   * Creates a new vector which represents the vector scaled by the scalar.
   *
   * @param scalar the scalar to multiply the vector with
   * @return a new vector which is the vector scaled by the scalar
   * @throws IllegalArgumentException if the scalar is {@link Double#NaN}
   */
  public @NotNull Vector multiply(double scalar) throws IllegalArgumentException {
    return new Vector(x0 * scalar, x1 * scalar);
  }

  /**
   * Creates a new vector which represents the vector divided by the scalar.
   *
   * @param scalar the scalar to divide the vector by
   * @return a new vector which is the vector divided by the scalar
   * @throws IllegalArgumentException if the scalar is zero or {@link Double#NaN}
   */
  public @NotNull Vector divide(double scalar) throws IllegalArgumentException {
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
   * @throws IllegalArgumentException if either of the given vectors are null
   */
  public static double dot(@NotNull Vector a, @NotNull Vector b) throws IllegalArgumentException {
    return a.getX0() * b.getX0() + a.getX1() * b.getX1();
  }

  /**
   * Returns the dot product of this vector and the given vector.
   *
   * @param vector the vector to calculate the dot product with
   * @return the dot product of this vector and the given vector
   * @throws IllegalArgumentException if the given vector is null
   */
  public double dot(@NotNull Vector vector) throws IllegalArgumentException {
    return Vector.dot(this, vector);
  }

  /**
   * Calculates the angle between two vectors and returns the result.
   *
   * @param a the first vector
   * @param b the second vector
   * @return the angle between the two vectors
   * @throws IllegalArgumentException if either of the given vectors are null
   */
  public static double angle(@NotNull Vector a, @NotNull Vector b) throws IllegalArgumentException {
    return Math.acos(
        Vector.dot(a, b) / (a.length() * b.length())
    );
  }

  /**
   * Returns the angle between this vector and the given vector.
   *
   * @param vector the vector to calculate the angle with
   * @return the angle between this vector and the given vector
   * @throws IllegalArgumentException if the given vector is null
   */
  public double angle(@NotNull Vector vector) throws IllegalArgumentException {
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

  /**
   * Returns the hash code of the vector based on its x0 and x1 components.
   *
   * @return the hash code of the vector
   */
  @Override
  public int hashCode() {
    // Generated by IntelliJ IDEA
    return Objects.hash(x0, x1);
  }

  /**
   * Returns a simple string representation of the vector.
   *
   * @return a simple string representation of the vector
   */
  public @NotNull String asSimpleString() {
    return "(" + x0 + ", " + x1 + ")";
  }

  /**
   * Returns a string representation of the vector.
   *
   * @return a string representation of the vector
   */
  public @NotNull String toString() {
    return new ToStringBuilder(this)
        .field("x0", x0)
        .field("x1", x1)
        .build();
  }
}
