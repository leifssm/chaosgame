package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a julia transformation.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class JuliaTransformation {
  private final ComplexNumber complexNumber;

  /**
   * Creates a new instance with the given complex number.
   *
   * @param c the complex number to use in the transformation
   */
  public JuliaTransformation(ComplexNumber c) {
    this.complexNumber = c;
  }

  /**
   * Transforms the given complex number using the complex number of this transformation, and
   * randomly negates the result.
   *
   * @param z the complex number to transform
   * @return the transformed complex number
   */
  public ComplexNumber transform(@NotNull ComplexNumber z) {
    ComplexNumber transformed = z
        .subtract(complexNumber)
        .sqrt();
    if (Math.random() < 0.5) {
      return transformed;
    }
    return transformed.multiply(-1);
  }

}
