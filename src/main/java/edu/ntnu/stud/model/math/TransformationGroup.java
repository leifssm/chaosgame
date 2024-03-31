package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

/**
 * Represents a group of affine transformations. The group is a collection of transformations which
 * are randomly picked every time the transform method is called.
 *
 * @version 1.3
 * @author Leif Mørstad
 */
public class TransformationGroup implements Transform2D {
  private final Random random = new Random();
  private final Transform2D[] transformations;

  /**
   * Creates a new instance with the given transformations.
   *
   * @param transformations the transformations in the group
   * @throws IllegalArgumentException if the array of transformations is empty
   */
  public TransformationGroup(Transform2D... transformations) throws IllegalArgumentException {
    if (transformations.length == 0) {
      throw new IllegalArgumentException("A transformation group must contain at least one transformation");
    }
    this.transformations = transformations;
  }

  /**
   * Creates a new instance with the given transformations.
   *
   * @param transformations the transformations in the group
   * @throws IllegalArgumentException if the array of transformations is empty
   */
  public TransformationGroup(List<Transform2D> transformations) throws IllegalArgumentException {
    if (transformations.isEmpty()) {
      throw new IllegalArgumentException("A transformation group must contain at least one transformation");
    }
    this.transformations = transformations.toArray(new Transform2D[0]);
  }

  /**
   * Transforms the given vector using a randomly picked transformation from the group.
   *
   * @param vector the vector to transform
   * @return the randomly transformed vector
   */
  public @NotNull Vector transform(@NotNull Vector vector) {
    int index = random.nextInt(transformations.length);
    return transformations[index].transform(vector);
  }
}
