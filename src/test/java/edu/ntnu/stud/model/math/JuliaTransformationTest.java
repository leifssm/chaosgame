package edu.ntnu.stud.model.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static edu.ntnu.stud.model.math.VectorTestUtils.assertVectorEquals;

public class JuliaTransformationTest {
  @Nested
  class PositiveTests {
    @Test
    @DisplayName("Constructor accepts booleans and ints")
    void constructor() {
      new JuliaTransformation(new ComplexNumber(1, 0), 1);
      new JuliaTransformation(new ComplexNumber(1, 0), -1);
      new JuliaTransformation(new ComplexNumber(1, 0), 0);
      new JuliaTransformation(new ComplexNumber(1, 0), true);
      new JuliaTransformation(new ComplexNumber(1, 0), false);
    }

    @Test
    @DisplayName("Transforms complex numbers")
    void transformsComplexNumber() {
      ComplexNumber c = new ComplexNumber(4, 6);
      ComplexNumber z = new ComplexNumber(2, 3);
      JuliaTransformation jt = new JuliaTransformation(c, 1);
      Vector result = jt.transform(z);

      assertVectorEquals(
          0.895977476129838,
          -1.6741492280355401,
          result,
          "The transformation of the complex number should succeed"
      );
    }

    @Test
    @DisplayName("Transforms vectors")
    void transformsVector() {
      ComplexNumber c = new ComplexNumber(4, 6);
      Vector z = new Vector(2, 3);
      JuliaTransformation jt = new JuliaTransformation(c, 1);
      Vector result = jt.transform(z);

      assertVectorEquals(
          0.895977476129838,
          -1.6741492280355401,
          result,
          "The transformation of a vector should succeed"
      );
    }
  }

  @Nested
  class NegativeTests {
    @Test
    @DisplayName("Constructor throws when given null")
    void constructor() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new JuliaTransformation(null, 1),
          "The constructor should throw when given null"
      );

      assertThrows(
          IllegalArgumentException.class,
          () -> new JuliaTransformation(null, true),
          "The constructor should throw when given null"
      );
    }

    @Test
    @DisplayName("Transforming throws when given null")
    void transform() {
      JuliaTransformation jt = new JuliaTransformation(
          new ComplexNumber(1, 0),
          1
      );

      assertThrows(
          IllegalArgumentException.class,
          () -> jt.transform((ComplexNumber) null),
          "The transformation should throw when given null"
      );

      assertThrows(
          IllegalArgumentException.class,
          () -> jt.transform((Vector) null),
          "The transformation should throw when given null"
      );
    }
  }
}