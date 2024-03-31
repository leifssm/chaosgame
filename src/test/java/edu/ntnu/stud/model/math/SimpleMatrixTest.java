package edu.ntnu.stud.model.math;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.runner.RunWith;

import static edu.ntnu.stud.model.math.VectorTestUtils.assertVectorEquals;
import static edu.ntnu.stud.model.math.VectorTestUtils.assertVectorNotEquals;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(Enclosed.class)
public class SimpleMatrixTest {
  @Nested
  class PositiveTests {
    @Test
    @DisplayName("Constructor does not throw exception when given any doubles")
    public void testConstructor() {
      new SimpleMatrix(1, 2, 3, 4);
      new SimpleMatrix(0, 0, 0, 0);
      new SimpleMatrix(-1, -2, -3, -4);
    }

    @Test
    @DisplayName("Multiplying matrix with vector returns correct vector")
    public void testMultiply() {
      SimpleMatrix matrix = new SimpleMatrix(1, 2, 3, 4);
      Vector vector = new Vector(5, 6);
      Vector result = matrix.transform(vector);

      assertVectorEquals(
          1 * 5 + 2 * 6,
          3 * 5 + 4 * 6,
          result,
          "Multiplying matrix with vector should return correct vector"
      );
    }

    @Test
    @DisplayName("Test get")
    public void testGet() {
      SimpleMatrix matrix = new SimpleMatrix(1, 2, 3, 4);

      assertEquals(1, matrix.getA00());
      assertEquals(2, matrix.getA10());
      assertEquals(3, matrix.getA01());
      assertEquals(4, matrix.getA11());
    }
  }

  @Nested
  class NegativeTests {
    @Test
    @DisplayName("Multiplying matrix with null vector throws an exception")
    public void getTest() {
      SimpleMatrix matrix = new SimpleMatrix(1, 2, 3, 4);
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> matrix.transform(null),
          "Multiplying the matrix with a null vector should throw an exception"
      );
    }
  }

}