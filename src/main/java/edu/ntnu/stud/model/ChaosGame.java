package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.TransformationGroup;
import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing all needed information concerning the running and displaying of a fractal
 * display.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ChaosGame {
  /**
   * The canvas on which the fractal is drawn.
   */
  private final @NotNull ChaosGameCanvas canvas;

  /**
   * The transformations used to generate the fractal.
   */
  private final @NotNull TransformationGroup transformations;

  /**
   * The current point where the fractal is drawn from. Starts at (0, 0)
   */
  private @NotNull Vector currentPoint = new Vector(0, 0);

  /**
   * Creates a new instance with the given width, height and description.
   *
   * @param width the width of the canvas
   * @param height the height of the canvas
   * @param description the description of the chaos game
   * @throws IllegalArgumentException if description is null
   */
  public ChaosGame(
      int width,
      int height,
      @NotNull ChaosGameDescription description
  ) throws IllegalArgumentException {
    this.transformations = description.transformations();
    this.canvas = new ChaosGameCanvas(
        width,
        height,
        description.minCoords(),
        description.maxCoords()
    );
    canvas.drawAtCoords(currentPoint);
  }

  public @NotNull TransformationGroup getTransformations() {
    return transformations;
  }

  public @NotNull ChaosGameCanvas getCanvas() {
    return canvas;
  }

  /**
   * Iterates the transformations randomly a given number of times, and updates the canvas.
   *
   * @param steps the number of iterations to perform
   */
  public void iterate(int steps) {
    for (int i = 0; i < steps; i++) {
      iterate();
    }
  }

  /**
   * Iterates the point once, by randomly transforming the point, and draws it on the canvas.
   */
  public void iterate() {
    currentPoint = transformations.transform(currentPoint);
    canvas.drawAtCoords(currentPoint);
  }
}
