package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a julia transformation.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class JuliaTransformation implements Transform2D {
  private final ComplexNumber complexNumber;
  private final int sign;

  /**
   * Creates a new instance with the given complex number and given sign.
   *
   * @param c the complex number to use in the transformation
   */
  public JuliaTransformation(ComplexNumber c, int sign) {
    this.complexNumber = c;
    this.sign = sign >= 0 ? 1 : -1;
  }

  /**
   * Creates a new instance with the given complex number and a positive sign if given true.
   *
   * @param c the complex number to use in the transformation
   * @param positive whether the sign should be positive
   */
  public JuliaTransformation(ComplexNumber c, boolean positive) {
    this(c, positive ? 1 : -1);
  }

  /**
   * Transforms the given complex number using the complex number of this transformation, and
   * randomly negates the result.
   *
   * @param z the complex number to transform
   * @return the transformed complex number
   */
  public Vector transform(@NotNull ComplexNumber z) {
    return z
        .subtract(complexNumber)
        .sqrt()
        .multiply(sign);
  }

  /**
   * Transforms the given vector by converting it to a complex number and calling the transformation
   * on the complex number.
   *
   * @param point the vector to transform
   * @return the transformed vector
   */
  public Vector transform(Vector point) {
    return transform(new ComplexNumber(point));
  }
}
