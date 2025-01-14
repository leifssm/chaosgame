package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * An immutable complex number with relevant methods.
 *
 * @author Leif Mørstad
 * @version 1.2
 */
public class ComplexNumber extends Vector {

  /**
   * Creates a new instance with the given real and imaginary values.
   *
   * @param real      the real value of the complex number
   * @param imaginary the imaginary value of the complex number
   * @throws IllegalArgumentException if any of the doubles are {@link Double#NaN}
   */
  public ComplexNumber(double real, double imaginary) throws IllegalArgumentException {
    super(real, imaginary);
  }

  /**
   * Creates a new instance with the given real and imaginary values.
   *
   * @param values an array with exactly two numerical values, one real and one imaginary.
   * @throws IllegalArgumentException if the array does not have exactly two values, or if any of
   *                                  the doubles are {@link Double#NaN}
   */
  public ComplexNumber(double @NotNull [] values) throws IllegalArgumentException {
    super(values);
  }

  /**
   * Returns the square root of the given complex number.
   *
   * @param z the complex number to find the square root of
   * @return the square root of the complex number
   * @throws IllegalArgumentException if the given complex number is null
   */
  public static @NotNull ComplexNumber sqrt(@NotNull ComplexNumber z) {
    double length = z.length();
    double real = Math.sqrt((length + z.getX0()) / 2);
    double sign = Math.signum(z.getX1());
    double imaginary = sign * Math.sqrt((length - z.getX0()) / 2);
    return new ComplexNumber(real, imaginary);
  }

  /**
   * Creates a new instance with the given vector.
   *
   * @param vector the vector to copy as a complex number
   * @throws IllegalArgumentException if given a null vector
   */
  public static @NotNull ComplexNumber fromVector(
      @NotNull Vector vector
  ) throws IllegalArgumentException {
    return new ComplexNumber(vector.getX0(), vector.getX1());
  }

  /**
   * Returns the square root of the complex number.
   *
   * @return the square root of the complex number
   */
  public @NotNull ComplexNumber sqrt() {
    return ComplexNumber.sqrt(this);
  }

  // +-------------------------------------------------------------------------------------+
  // | The following methods are overridden to return a ComplexNumber instead of a Vector. |
  // +-------------------------------------------------------------------------------------+

  /**
   * Adds the given complex number to this complex number.
   *
   * @throws IllegalArgumentException if the given vector is null
   * @see Vector#add(Vector)
   */
  @Override
  public @NotNull ComplexNumber add(@NotNull Vector other) throws IllegalArgumentException {
    return ComplexNumber.fromVector(
        super.add(other)
    );
  }

  /**
   * Subtracts the given complex number from this complex number.
   *
   * @throws IllegalArgumentException if the given vector is null
   * @see Vector#subtract(Vector)
   */
  @Override
  public @NotNull ComplexNumber subtract(@NotNull Vector other) throws IllegalArgumentException {
    return ComplexNumber.fromVector(
        super.subtract(other)
    );
  }

  /**
   * Multiplies the given complex number with a scalar.
   *
   * @throws IllegalArgumentException if the scalar is {@link Double#NaN}
   * @see Vector#multiply(double)
   */
  @Override
  public @NotNull ComplexNumber multiply(double scalar) throws IllegalArgumentException {
    return ComplexNumber.fromVector(
        super.multiply(scalar)
    );
  }

  /**
   * Divides the given complex number with a scalar.
   *
   * @throws IllegalArgumentException if the scalar is 0 or {@link Double#NaN}
   * @see Vector#divide(double)
   */
  @Override
  public @NotNull ComplexNumber divide(double scalar) throws IllegalArgumentException {
    return ComplexNumber.fromVector(
        super.divide(scalar)
    );
  }
}
