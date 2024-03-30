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
   * @param real the real value of the complex number
   * @param imaginary the imaginary value of the complex number
   */
  public ComplexNumber(double real, double imaginary) {
    super(real, imaginary);
  }

  /**
   * Creates a new instance with the given real and imaginary values.
   *
   * @param values an array with exactly two numerical values, one real and one imaginary.
   * @throws IllegalArgumentException if the array does not have exactly two values
   */
  public ComplexNumber(double[] values) throws IllegalArgumentException {
    super(values);
  }

  /**
   * Creates a new instance with the given vector.
   *
   * @param vector the vector to copy as a complex number
   */
  public ComplexNumber(@NotNull Vector vector) {
    super(vector.getX0(), vector.getX1());
  }

  /**
   * Returns the square root of the given complex number.
   *
   * @param z the complex number to find the square root of
   * @return the square root of the complex number
   */
  public static @NotNull ComplexNumber sqrt(@NotNull ComplexNumber z) {
    double length = z.length();
    double real = Math.sqrt((length + z.getX0()) / 2);
    double sign = Math.signum(z.getX1());
    double imaginary = sign * Math.sqrt((length - z.getX0()) / 2);
    return new ComplexNumber(real, imaginary);
  }

  /**
   * Returns the square root of the complex number.
   *
   * @return the square root of the complex number
   */
  public @NotNull ComplexNumber sqrt() {
    return ComplexNumber.sqrt(this);
  }

  /**
   * @see Vector#add(Vector)
   */
  @Override
  public @NotNull ComplexNumber add(@NotNull Vector other) {
    return new ComplexNumber(
        super.add(other)
    );
  }

  /**
   * @see Vector#subtract(Vector)
   */
  @Override
  public @NotNull ComplexNumber subtract(@NotNull Vector other) {
    return new ComplexNumber(
        super.subtract(other)
    );
  }

  /**
   * @see Vector#multiply(double)
   */
  @Override
  public @NotNull ComplexNumber multiply(double scalar) {
    return new ComplexNumber(
        super.multiply(scalar)
    );
  }

  /**
   * @see Vector#divide(double)
   */
  @Override
  public @NotNull ComplexNumber divide(double scalar) {
    return new ComplexNumber(
        super.divide(scalar)
    );
  }
}
