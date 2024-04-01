package edu.ntnu.stud.model.math;

import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(Enclosed.class)
public class TransformationGroupTest {
  @Nested
  class PositiveTests {
    @Test
    @DisplayName("Instantiating should succeed when given transformations.")
    void constructor() {
      Transform2D t1 = new AffineTransformation(
          new SimpleMatrix(0.5, 0, 0, 0.5),
          new Vector(0, 0)
      );
      Transform2D t2 = new AffineTransformation(
          new SimpleMatrix(0.5, 0, 0, 0.5),
          new Vector(0.5, 0)
      );

      new TransformationGroup(
          t1,
          t2
      );
      new TransformationGroup(List.of(t1,t2));
    }

    @RepeatedTest(10)
    @DisplayName("Transform should return a randomly transformed vector.")
    void transform() {
      Transform2D t1 = new AffineTransformation(
          new SimpleMatrix(0.5, 0, 0, 0.5),
          new Vector(0, 0)
      );
      Transform2D t2 = new AffineTransformation(
          new SimpleMatrix(0.5, 0, 0, 0.5),
          new Vector(0.5, 0)
      );

      Vector v = new Vector(1, 1);

      Vector expected1 = t1.transform(v);
      Vector expected2 = t2.transform(v);

      TransformationGroup group = new TransformationGroup(
          t1,
          t2
      );

      Vector result = group.transform(v);

      // The result should be one of the expected vectors
      try {
        assertEquals(expected1, result);
      } catch (AssertionError e) {
        assertEquals(expected2, result);
      }
    }
  }

  @Nested
  class NegativeTests {
    @Test
    @DisplayName("Constructor should throw when given no elements")
    void constructor() {
      assertThrows(
          IllegalArgumentException.class,
          () -> new TransformationGroup(),
          "Expected an empty transformation group to throw an exception"
      );

      assertThrows(
          IllegalArgumentException.class,
          () -> new TransformationGroup(new ArrayList<>()),
          "Expected an empty transformation group to throw an exception"
      );
    }
  }
}