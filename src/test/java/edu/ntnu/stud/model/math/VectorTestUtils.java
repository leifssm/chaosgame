package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class VectorTestUtils {
  /**
   * Asserts that the given vector is equal to the given values, or throws an exception with
   * appended extra information.
   *
   * @param x0 the expected x0 value of the vector
   * @param x1 the expected x1 value of the vector
   * @param actual the vector to compare to
   * @param errorMessage the error message to display if the assertion fails
   */
  public static void assertVectorEquals(
      double x0,
      double x1,
      @NotNull Vector actual,
      @NotNull String errorMessage
  ) {
    String message = errorMessage
        + " (got " + actual.asString()
        + " but expected (" + x0 + ", " + x1 + "))";
    assertEquals(x0, actual.getX0(), message);
    assertEquals(x1, actual.getX1(), message);
  }

  /**
   * Asserts that the given vector is not equal to the given values, or throws an exception with
   * appended extra information.
   *
   * @param x0 the unexpected x0 value of the vector
   * @param x1 the unexpected x1 value of the vector
   * @param actual the vector to compare to
   * @param errorMessage the error message to display if the assertion succeeds
   */
  public static void assertVectorNotEquals(
      double x0,
      double x1,
      @NotNull Vector actual,
      @NotNull String errorMessage
  ) {
    assertVectorNotEquals(
        new Vector(x0, x1),
        actual,
        errorMessage
    );
  }

  /**
   * Asserts that the given vectors are not equal, or throws an exception with appended extra
   * information.
   *
   * @param expected the unexpected vector
   * @param actual the actual vector to compare to
   * @param errorMessage the error message to display if the vectors are equal
   * @see #assertVectorNotEquals(double, double, Vector, String)
   */
  public static void assertVectorNotEquals(
      @NotNull Vector expected,
      @NotNull Vector actual,
      @NotNull String errorMessage
  ) {
    assertNotEquals(
        expected,
        actual,
        errorMessage
            + " (got " + actual.asString()
            + " and expected " + expected.asString() + ")"
    );
  }
}
