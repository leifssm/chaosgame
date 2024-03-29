package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a group of affine transformations. The group is a collection of transformations which
 * are randomly picked every time the transform method is called.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class AffineTransformationGroup {
  /**
   * A group of affine transformations which creates a Sierpinski triangle.
   */
  static final AffineTransformationGroup sierpinski = new AffineTransformationGroup(
      new AffineTransformation(
          new SimpleMatrix(0.5, 0, 0, 0.5),
          new Vector(0, 0)
      ),
      new AffineTransformation(
          new SimpleMatrix(0.5, 0, 0, 0.5),
          new Vector(0.5, 0)
      ),
      new AffineTransformation(
          new SimpleMatrix(0.5, 0, 0, 0.5),
          new Vector(0.25, 0.5)
      )
  );

  /**
   * A group of affine transformations which creates a Barnsley fern.
   */
  static final AffineTransformationGroup barnsley = new AffineTransformationGroup(
      new AffineTransformation(
          new SimpleMatrix(0, 0, 0, 0.16),
          new Vector(0, 0)
      ),
      new AffineTransformation(
          new SimpleMatrix(0.85, 0.04, -0.04, 0.85),
          new Vector(0, 1.6)
      ),
      new AffineTransformation(
          new SimpleMatrix(0.2, -0.26, 0.23, 0.22),
          new Vector(0, 1.6)
      ),
      new AffineTransformation(
          new SimpleMatrix(-0.15, 0.28, 0.26, 0.24),
          new Vector(0, 0.44)
      )
  );

  private final AffineTransformation[] transformations;

  /**
   * Creates a new instance with the given transformations.
   *
   * @param transformations the transformations in the group
   */
  public AffineTransformationGroup(AffineTransformation... transformations) {
    this.transformations = transformations;
  }

  /**
   * Transforms the given vector using a randomly picked transformation from the group.
   *
   * @param vector the vector to transform
   * @return the randomly transformed vector
   */
  public @NotNull Vector transform(@NotNull Vector vector) {
    int index = (int) (Math.random() * transformations.length);
    return transformations[index].transform(vector);
  }
}
