package edu.ntnu.stud.model.math;

import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static edu.ntnu.stud.model.math.VectorTestUtils.assertVectorEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(Enclosed.class)
public class ComplexNumberTest {
  @Nested
  class PositiveTests {
    @Test
    @DisplayName("Constructor takes any two doubles")
    public void constructor() {
      new ComplexNumber(1.0, 2.0);
      new ComplexNumber(1.0, 0.0);
      new ComplexNumber(0.0, 1.0);
      new ComplexNumber(0.0, 0.0);
      new ComplexNumber(-1, -1);
    }

    @Test
    @DisplayName("Constructor takes an array of two doubles")
    public void constructorTakesArray() {
      new ComplexNumber(new double[]{ 1.0, 2.0 });
    }

    @Test
    @DisplayName("sqrt() returns the square root of the complex number")
    public void sqrtReturnsCorrectValue() {
      ComplexNumber sqrt1 = new ComplexNumber(0, 4).sqrt();
      ComplexNumber sqrt2 = new ComplexNumber(4, 0).sqrt();
      ComplexNumber sqrt3 = new ComplexNumber(7, 24).sqrt();
      ComplexNumber sqrt4 = new ComplexNumber(7, -24).sqrt();
      ComplexNumber sqrt5 = new ComplexNumber(-7, 24).sqrt();
      ComplexNumber sqrt6 = new ComplexNumber(-7, -24).sqrt();

      double sq2 = Math.sqrt(2);

      assertVectorEquals(
          sq2,
          sq2,
          sqrt1,
          "sqrt(0, 4) should return √2 + √2i"
      );
      assertVectorEquals(
          2,
          0,
          sqrt2,
          "sqrt(4, 0) should return 2 + 0i"
      );
      assertVectorEquals(
          4,
          3,
          sqrt3,
          "sqrt(7, 24) should return 4 + 3i"
      );
      assertVectorEquals(
          4,
          -3,
          sqrt4,
          "sqrt(7, -24) should return 4 - 3i"
      );
      assertVectorEquals(
          3,
          4,
          sqrt5,
          "sqrt(-7, 24) should return 3 + 4i"
      );
      assertVectorEquals(
          3,
          -4,
          sqrt6,
          "sqrt(-7, -24) should return 3 - 4i"
      );
    }

    @Test
    @DisplayName("add() returns a complex number and not a vector")
    public void addReturnsComplexNumber() {
      ComplexNumber complexNumber = new ComplexNumber(1, 2);
      ComplexNumber result = complexNumber.add(new Vector(3, 4));

      assertInstanceOf(
          ComplexNumber.class,
          result,
          "Adding a vector to a complex number should return a complex number"
      );
      assertVectorEquals(
          4,
          6,
          result,
          "Adding (1, 2) and (3, 4) should return (4, 6)"
      );
    }

    @Test
    @DisplayName("subtract() returns a complex number and not a vector")
    public void subtractReturnsComplexNumber() {
      ComplexNumber complexNumber = new ComplexNumber(1, 2);
      ComplexNumber result = complexNumber.subtract(new Vector(3, 4));

      assertInstanceOf(
          ComplexNumber.class,
          result,
          "Subtracting a vector from a complex number should return a complex number"
      );
      assertVectorEquals(
          -2,
          -2,
          result,
          "Subtracting (1, 2) and (3, 4) should return (-2, -2)"
      );
    }

    @Test
    @DisplayName("multiply() returns a complex number and not a vector")
    public void multiplyReturnsComplexNumber() {
      ComplexNumber complexNumber = new ComplexNumber(1, 2);
      ComplexNumber result = complexNumber.multiply(3);

      assertInstanceOf(
          ComplexNumber.class,
          result,
          "Multiplying a complex number with a scalar should return a complex number"
      );
      assertVectorEquals(
          3,
          6,
          result,
          "Multiplying (1, 2) with 3 should return (3, 6)"
      );
    }

    @Test
    @DisplayName("divide() returns a complex number and not a vector")
    public void divideReturnsComplexNumber() {
      ComplexNumber complexNumber = new ComplexNumber(3, 6);
      ComplexNumber result = complexNumber.divide(3);

      assertInstanceOf(
          ComplexNumber.class,
          result,
          "Dividing a complex number with a scalar should return a complex number"
      );
      assertVectorEquals(
          1,
          2,
          result,
          "Dividing (1, 2) with 3 should return (1/3, 2/3)"
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
    @DisplayName("Constructor throws with an array of the wrong length")
    public void constructorTakesArray() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new ComplexNumber(new double[]{ 1.0, 2.0, 3.0 }),
          "Instantiating ComplexNumber with an array with more than 2 numbers should " +
              "throw an exception"
      );
      assertThrows(
          IllegalArgumentException.class,
          () -> new ComplexNumber(new double[]{ 1.0 }),
          "Instantiating ComplexNumber with an array with less than 2 numbers should " +
              "throw an exception"
      );
    }

    @Test
    @DisplayName("Constructor throws when given null")
    public void constructorThrowsOnANull() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new ComplexNumber((Vector) null),
          "Instantiating ComplexNumber with null should throw an exception"
      );
    }
  }
}