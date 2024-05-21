package edu.ntnu.stud.model.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static edu.ntnu.stud.model.math.VectorTestUtils.assertVectorEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AffineTransformationTest {

  @Nested
  class PositiveTests {

    @Test
    @DisplayName("Constructor with six values should not throw an exception")
    void constructorWithNumbers() {
      new AffineTransformation(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("Constructor with a defined matrix and vector should not throw an exception")
    void constructorWithObjects() {
      new AffineTransformation(
          new SimpleMatrix(1, 2, 3, 4),
          new Vector(5, 6)
      );
    }

    @Test
    @DisplayName("Constructor with no arguments creates an identity transformation")
    void constructorWithNoArgs() {
      Vector vector = new Vector(1, 2);

      Vector transformedVector = new AffineTransformation().transform(vector);

      assertVectorEquals(
          vector.getX0(), // 1
          vector.getX1(), // 2
          transformedVector,
          "An identity transformation should not change the vector"
      );
    }

    @Test
    @DisplayName("Transforming a vector with a transformation should work")
    void transform() {
      AffineTransformation transformation = new AffineTransformation(
          new SimpleMatrix(1, 2, 3, 4),
          new Vector(5, 6)
      );
      Vector vector = new Vector(7, 8);

      Vector transformedVector = transformation.transform(vector);
      assertVectorEquals(
          (1 * 7 + 2 * 8) + 5,
          (3 * 7 + 4 * 8) + 6,
          transformedVector,
          "The transformation should multiply the vector with the matrix and add the " +
              "translation"
      );
    }
  }

  @Nested
  class NegativeTests {

    @Test
    @DisplayName("Constructor throws with invalid arguments")
    void constructor() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new AffineTransformation(1, 2, 3, 4, 5),
          "Creating an AffineTransformation with not exactly six values should " +
              "throw an exception"
      );
      assertThrows(
          IllegalArgumentException.class,
          () -> new AffineTransformation(1, 2, 3, 4, 5, 6, 7),
          "Creating an AffineTransformation with not exactly six values should " +
              "throw an exception"
      );
    }
  }
}