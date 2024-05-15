package edu.ntnu.stud.model.math;

import static edu.ntnu.stud.model.math.VectorTestUtils.assertVectorEquals;
import static edu.ntnu.stud.model.math.VectorTestUtils.assertVectorNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VectorTest {

  @Nested
  class PositiveTests {

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
    @DisplayName("Adding vectors should return a new correct vector")
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
    @DisplayName("Subtracting vectors should return a new correct vector")
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
    @DisplayName("Multiplying a vector should return a new correct vector")
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
    @DisplayName("Dividing a vector should return a new correct vector")
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
    @DisplayName("Dot product of two vectors should be correct")
    public void dot() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(3, 4);

      double result = Vector.dot(vector1, vector2);

      assertEquals(11, result, "The dot product of the two vectors should be 11");
    }

    @Test
    @DisplayName("Dot product of two parallel vectors should be 0")
    public void dotParallel() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(2, -1);

      double result = Vector.dot(vector1, vector2);

      assertEquals(0, result, "The dot product of two parallel vectors should be 0");
    }

    @Test
    @DisplayName("Angle between two vectors should be correct")
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
    @DisplayName("Angle between two equal vectors should be 0")
    public void angleIsZero() {
      Vector vector1 = new Vector(1, 0);
      Vector vector2 = new Vector(1, 0);

      assertEquals(
          0,
          Vector.angle(vector1, vector2),
          "The angle between two equal vectors should be 0"
      );
    }

    @Test
    @DisplayName("Angle between two vectors in opposite directions should be 180")
    public void angleIs180() {
      Vector vector1 = new Vector(1, 0);
      Vector vector2 = new Vector(-1, 0);

      assertEquals(
          Math.PI,
          Vector.angle(vector1, vector2),
          "The angle between two parallel vectors should be 180deg"
      );
    }

    @Test
    @DisplayName("asString() should return a simple string representation")
    public void asString() {
      Vector vector = new Vector(1, 2);
      assertEquals(
          "(1.0, 2.0)",
          vector.asSimpleString(),
          "The string representation should be (1.0, 2.0)"
      );
    }

    @Test
    @DisplayName("equals() should return test quality based on position")
    public void equals() {
      Vector vector1 = new Vector(1, 2);
      Vector vector2 = new Vector(1, 2);
      Vector vector3 = new Vector(2, 1);

      assertEquals(vector1, vector2, "Two equal vectors should be equal");
      assertNotEquals(vector1, vector3, "Two different vectors should not be equal");
    }

    @Test
    @DisplayName("toString() should return a detailed string representation")
    public void toStringTest() {
      Vector vector = new Vector(1, 2);
      assertEquals(
          "Vector[x0=1.0, x1=2.0]",
          vector.toString(),
          "The string representation should be Vector[x0=1.0, x1=2.0]"
      );
    }
  }

  @Nested
  class NegativeTests {

    @Test
    @DisplayName("Constructor throws with NaN")
    public void constructorThrowsWithNaN() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new Vector(Double.NaN, 0),
          "Vector(Double.NaN, 0) should throw an exception"
      );
      assertThrows(
          IllegalArgumentException.class,
          () -> new Vector(0, Double.NaN),
          "Vector(0, Double.NaN) should throw an exception"
      );
    }

    @Test
    @DisplayName("Constructor throws when dividing by zero")
    public void divideShouldThrowWithDividingByZero() {
      Vector vector = new Vector(1, 2);

      assertThrows(
          IllegalArgumentException.class,
          () -> vector.divide(0),
          "Dividing by zero should throw an exception"
      );
    }
  }
}
