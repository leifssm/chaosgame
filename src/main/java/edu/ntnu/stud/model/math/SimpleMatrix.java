package edu.ntnu.stud.model.math;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.stud.utils.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * A class representing a simple two-by-two matrix.
 *
 * @param a00 The top left corner of the matrix. Cannot be {@link Double#NaN}
 * @param a01 The top right corner of the matrix. Cannot be {@link Double#NaN}
 * @param a10 The bottom left corner of the matrix. Cannot be {@link Double#NaN}
 * @param a11 The bottom right corner of the matrix. Cannot be {@link Double#NaN}
 * @author Leif Mørstad
 * @version 2.1
 */
public record SimpleMatrix(double a00, double a01, double a10, double a11) implements Transform2D {

  /**
   * Creates a new two-by-two matrix with the given values.
   *
   * @param a00 the value at position (0, 0)
   * @param a01 the value at position (0, 1)
   * @param a10 the value at position (1, 0)
   * @param a11 the value at position (1, 1)
   * @throws IllegalArgumentException if any of the values are {@link Double#NaN}
   */
  public SimpleMatrix {
    if (Double.isNaN(a00) || Double.isNaN(a01) || Double.isNaN(a10) || Double.isNaN(a11)) {
      throw new IllegalArgumentException("Matrix values cannot be NaN");
    }
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

  @Override
  @JsonIgnore
  public @NotNull String getType() {
    return "Matrix";
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .field("a00", a00)
        .field("a01", a01)
        .field("a10", a10)
        .field("a11", a11)
        .build();
  }
}
