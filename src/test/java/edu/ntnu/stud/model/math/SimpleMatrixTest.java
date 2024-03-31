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
      Vector result = matrix.multiply(vector);

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

      assertEquals(1, matrix.get(0, 0));
      assertEquals(2, matrix.get(1, 0));
      assertEquals(3, matrix.get(0, 1));
      assertEquals(4, matrix.get(1, 1));
    }
  }

  @Nested
  class NegativeTests {
    @Test
    @DisplayName("Accessing out of bound values throws")
    public void getTest() {
      SimpleMatrix matrix = new SimpleMatrix(1, 2, 3, 4);
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> matrix.get(-1, 0),
          "Getting a value at an index that is not 0 or 1 should throw an exception"
      );
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> matrix.get(0, -1),
          "Getting a value at an index that is not 0 or 1 should throw an exception"
      );
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> matrix.get(2, 0),
          "Getting a value at an index that is not 0 or 1 should throw an exception"
      );
      assertThrows(
          ArrayIndexOutOfBoundsException.class,
          () -> matrix.get(0, 2),
          "Getting a value at an index that is not 0 or 1 should throw an exception"
      );
    }
  }

}