package edu.ntnu.stud.model.math;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a julia transformation. The transformation is a subtraction followed by a square root
 * and a multiplication. Identical to <pre>u = sqrt(z - c) * sign</pre>
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class JuliaTransformation implements Transform2D {

  /**
   * The complex number used in the transformation.
   */
  private final @NotNull ComplexNumber complexNumber;

  /**
   * The sign of the transformation. Is either 1 or -1.
   */
  private final int sign;

  /**
   * Creates a new instance with the given complex number and a positive sign if given true.
   *
   * @param c        the complex number to use in the transformation
   * @param positive whether the sign should be positive, false if negative
   * @throws IllegalArgumentException if the given complex number is null
   */
  public JuliaTransformation(
      @NotNull ComplexNumber c,
      boolean positive
  ) throws IllegalArgumentException {
    this(c, positive ? 1 : -1);
  }

  /**
   * Creates a new instance with the given complex number and given sign.
   *
   * @param c    the complex number to use in the transformation
   * @param sign the sign of the transformation, either a positive or negative integer. 0 is
   *             positive
   * @throws IllegalArgumentException if the given complex number is null
   */
  public JuliaTransformation(@NotNull ComplexNumber c, int sign) throws IllegalArgumentException {
    this.complexNumber = c;
    this.sign = sign >= 0 ? 1 : -1;
  }

  /**
   * Transforms the given vector by converting it to a complex number and calling the transformation
   * on the complex number.
   *
   * @param point the vector to transform
   * @return the transformed vector
   * @throws IllegalArgumentException if the given vector is null
   */
  public @NotNull Vector transform(@NotNull Vector point) throws IllegalArgumentException {
    return transform(ComplexNumber.fromVector(point));
  }

  /**
   * Transforms the given complex number using the complex number of this transformation, and
   * randomly negates the result.
   *
   * @param z the complex number to transform
   * @return the transformed complex number
   * @throws IllegalArgumentException if the given complex number is null
   */
  public @NotNull Vector transform(@NotNull ComplexNumber z) throws IllegalArgumentException {
    return z
        .subtract(complexNumber)
        .sqrt()
        .multiply(sign);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    JuliaTransformation that = (JuliaTransformation) o;

    return complexNumber.equals(that.complexNumber);
  }

  @Override
  public int hashCode() {
    return complexNumber.hashCode();
  }

  @Override
  public @NotNull String getType() {
    return "JuliaTransformation";
  }

  @JsonProperty
  public @NotNull ComplexNumber getComplexNumber() {
    return complexNumber;
  }

  @JsonIgnore
  public int getSign() {
    return sign;
  }
}
