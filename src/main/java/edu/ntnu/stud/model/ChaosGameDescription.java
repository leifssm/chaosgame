package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.AffineTransformationGroup;
import edu.ntnu.stud.model.math.Vector;

/**
 * A class representing the starting conditions to a fractal display
 *
 * @author Leif Mørstad
 * @version 0.1
 */
public class ChaosGameDescription {
  private final Vector minCoords;
  private final Vector maxCoords;
  private final AffineTransformationGroup transformations;

  public ChaosGameDescription(
      Vector minCoords,
      Vector maxCoords,
      AffineTransformationGroup transformations
  ) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transformations = transformations;
  }

  public Vector getMinCoords() {
    return minCoords;
  }

  public Vector getMaxCoords() {
    return maxCoords;
  }

  public AffineTransformationGroup getTransformations() {
    return transformations;
  }
}
