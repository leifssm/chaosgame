package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Enclosed.class)
public class VectorTest {
  /**
   * Asserts that the given vector is equal to the given values, or throws an exception with
   * appended extra information.
   *
   * @param x0 the expected x0 value of the vector
   * @param x1 the expected x1 value of the vector
   * @param actual the vector to compare to
   * @param errorMessage the error message to display if the assertion fails
   */
  private static void assertVectorEquals(
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
  private static void assertVectorNotEquals(
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
  private static void assertVectorNotEquals(
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

  @Nested
  public static class PositiveTests {
    @Test
    @DisplayName("Testing constructor")
    public void constructor() {
      Vector vector = new Vector(1, 2);
      assertEquals(1, vector.getX0());
      assertEquals(2, vector.getX1());
    }

    @Test
    @DisplayName("Testing constructor with (0, 0)")
    public void constructorWithZeroZero() {
      assertDoesNotThrow(
          () -> new Vector(0, 0),
          "Vector(0, 0) Should not throw an exception"
      );
    }

    @Test
    @DisplayName("Testing constructor with (-1, -1)")
    public void constructorWithNegativeOneOne() {
      assertDoesNotThrow(
          () -> new Vector(-1, -1),
          "Vector(-1, -1) Should not throw an exception"
      );
    }
    
    @Test
    @DisplayName("Testing length")
    public void length() {
      Vector vectorVariant1 = new Vector(3, 4);
      Vector vectorVariant2 = new Vector(3, -4);
      Vector vectorVariant3 = new Vector(-3, 4);
      Vector vectorVariant4 = new Vector(-3, -4);

      assertEquals(5, vectorVariant1.length(), "All variants should return 5");
      assertEquals(5, vectorVariant2.length(), "All variants should return 5");
      assertEquals(5, vectorVariant3.length(), "All variants should return 5");
      assertEquals(5, vectorVariant4.length(), "All variants should return 5");
    }

    @Test
    public void add() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(3, 4);

      Vector result = vector1.add(vector2);

      // Check that the original vectors are unchanged
      assertVectorEquals(1, 2, vector1, "The original vectors were mutated");
      assertVectorEquals(3, 4, vector2, "The original vectors were mutated");

      // Check that the result is as expected
      assertVectorEquals(
          4,
          6,
          result,
          "The resulting vector did not match the expected values"
      );

      // Check that the result is a new object
      assertVectorNotEquals(vector1, result, "The original vectors were mutated");
      assertVectorNotEquals(vector2, result, "The original vectors were mutated");
    }

    @Test
    public void subtract() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(3, 4);

      Vector result = vector1.subtract(vector2);

      // Check that the original vectors are unchanged
      assertVectorEquals(1, 2, vector1, "The original vectors were mutated");
      assertVectorEquals(3, 4, vector2, "The original vectors were mutated");

      // Check that the result is as expected
      assertVectorEquals(
          -2,
          -2,
          result,
          "The resulting vector did not match the expected values"
      );

      // Check that the result is a new object
      assertVectorNotEquals(vector1, result, "The original vectors were mutated");
      assertVectorNotEquals(vector2, result, "The original vectors were mutated");
    }

    @Test
    public void multiply() {
      Vector vector = new Vector(1, 2);
      Vector result = vector.multiply(3);

      // Check that the original vector is unchanged
      assertVectorEquals(1, 2, vector, "The original vector was mutated");

      // Check that the result is as expected
      assertVectorEquals(
          3,
          6,
          result,
          "The resulting vector did not match the expected values"
      );

      // Check that the result is a new object
      assertVectorNotEquals(vector, result, "The original vector was mutated");
    }

    @Test
    public void divide() {
      Vector vector = new Vector(1, 2);
      Vector result = vector.divide(2);

      // Check that the original vector is unchanged
      assertVectorEquals(1, 2, vector, "The original vector was mutated");

      // Check that the result is as expected
      assertVectorEquals(
          0.5,
          1,
          result,
          "The resulting vector did not match the expected values"
      );

      // Check that the result is a new object
      assertVectorNotEquals(vector, result, "The original vector was mutated");
    }

    @Test
    public void dot() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(3, 4);

      double result = Vector.dot(vector1, vector2);

      assertEquals(11, result, "The dot product of the two vectors should be 11");
    }

    @Test
    public void dotParallel() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(2, -1);

      double result = Vector.dot(vector1, vector2);

      assertEquals(0, result, "The dot product of two parallel vectors should be 0");
    }

    @Test
    public void angle() {
      Vector vector1 = new Vector(1, 0);
      Vector vector2 = new Vector(0, 1);

      double result = Vector.angle(vector1, vector2);

      assertEquals(
          Math.PI / 2,
          result,
          "The angle between the two vectors should be 90 degrees"
      );
    }

    @Test
    public void angleEqual() {
      Vector vector1 = new Vector(1, 0);
      Vector vector2 = new Vector(1, 0);

      double result = Vector.angle(vector1, vector2);

      assertEquals(
          0,
          result,
          "The angle between two parallel vectors should be 0"
      );
    }

    @Test
    public void asString() {
      Vector vector = new Vector(1, 2);
      assertEquals(
          "(1.0, 2.0)",
          vector.asString(),
          "The string representation should be (1.0, 2.0)"
      );
    }

    @Test
    public void equals() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(1, 2);
      Vector vector3 = new Vector(2, 1);

      assertEquals(vector1, vector2, "Two equal vectors should be equal");
      assertNotEquals(vector1, vector3, "Two different vectors should not be equal");
    }

    @Test
    public void toStringTest() {
      Vector vector = new Vector(1, 2);
      assertEquals(
          "Vector[x0=1.0, x1=2.0]",
          vector.toString(),
          "The string representation should be Vector[x=1.0, y=2.0]"
      );
    }
  }

  @Nested
  public class NegativeTests {

  }
}
