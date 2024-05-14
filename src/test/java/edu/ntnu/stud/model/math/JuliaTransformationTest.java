package edu.ntnu.stud.model.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
}