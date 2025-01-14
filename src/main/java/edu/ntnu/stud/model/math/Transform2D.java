package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a 2D transformation which takes a vector as an input and returns a new vector.
 *
 * @author Leif Mørstad
 * @version 1.1
 * @see Vector
 */
public interface Transform2D {
  /**
   * Returns the type of the transformation. Used for Serialization.
   *
   * @return the type of the transformation
   */
  @NotNull String getType();

  /**
   * Transforms the given vector.
   *
   * @param point the vector to transform
   * @return the transformed vector
   * @throws IllegalArgumentException if the given vector is null
   */
  @NotNull Vector transform(@NotNull Vector point) throws IllegalArgumentException;
}
