package edu.ntnu.stud.model.math;

import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a group of affine transformations. The group is a collection of transformations which
 * are randomly picked every time the transform method is called.
 *
 * @author Leif Mørstad
 * @version 1.3
 */
public class TransformationGroup implements Transform2D {

  /**
   * The random number generator used to pick a random transformation from the group.
   */
  private final @NotNull Random random = new Random();
  /**
   * The transformations in the group which are randomly chosen at each transformation. Cannot be
   * empty.
   */
  private final @NotNull Transform2D @NotNull [] transformations;

  /**
   * Creates a new instance with the given transformations.
   *
   * @param transformations the transformations in the group
   * @throws IllegalArgumentException if the array of transformations is empty
   */
  public TransformationGroup(Transform2D... transformations) throws IllegalArgumentException {
    if (transformations.length == 0) {
      throw new IllegalArgumentException(
          "A transformation group must contain at least one transformation"
      );
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
      throw new IllegalArgumentException(
          "A transformation group must contain at least one transformation"
      );
    }
    this.transformations = transformations.toArray(new Transform2D[0]);
  }

  /**
   * Transforms the given vector using a randomly picked transformation from the group.
   *
   * @param vector the vector to transform
   * @return the randomly transformed vector
   * @throws IllegalArgumentException if the given vector is null
   */
  public @NotNull Vector transform(@NotNull Vector vector) throws IllegalArgumentException {
    int index = random.nextInt(transformations.length);
    return transformations[index].transform(vector);
  }
}
