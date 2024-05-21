package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.TransformationGroup;
import edu.ntnu.stud.model.math.Vector;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing all needed information concerning the running and displaying of a fractal
 * display.
 *
 * @author Leif Mørstad
 * @version 1.2
 */
public class ChaosGame {

  /**
   * The scalar for the number of iterations to perform.
   */
  private static final int ITERATION_SCALAR = 1;
  /**
   * The canvas on which the fractal is drawn.
   */
  private final @NotNull ChaosGameCanvas canvas;
  /**
   * The original description of the chaos game.
   */
  private final @NotNull ChaosGameDescription description;
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
   * @param width       the width of the canvas
   * @param height      the height of the canvas
   * @param description the description of the chaos game
   * @throws IllegalArgumentException if description is null
   */
  public ChaosGame(
      int width,
      int height,
      @NotNull ChaosGameDescription description
  ) throws IllegalArgumentException {
    this.transformations = description.transformations();
    this.description = description;
    this.canvas = new ChaosGameCanvas(
        width,
        height,
        description.minCoords(),
        description.maxCoords()
    );
  }

  public @NotNull TransformationGroup getTransformations() {
    return transformations;
  }

  public @NotNull ChaosGameCanvas getCanvas() {
    return canvas;
  }

  public @NotNull ChaosGameDescription getDescription() {
    return description;
  }

  /**
   * Iterates the transformations randomly a given number of times, and updates the canvas.
   *
   * @param steps the number of iterations to perform
   */
  public void iterate(int steps) {
    Platform.runLater(() -> {
      for (int i = 0; i < steps; i++) {
        iterate();
      }
    });
  }

  /**
   * Returns the number of iterations the chaos game should perform.
   *
   * @return the number of iterations
   */
  private int getIterations() {
    return Math.min(
        1000000,
        getCanvas().getWidth() * getCanvas().getHeight() * ITERATION_SCALAR
    );
  }

  /**
   * Iterates the point once, by randomly transforming the point, and draws it on the canvas.
   */
  public void iterate() {
    currentPoint = getTransformations().transform(currentPoint);
    getCanvas().drawAtCoords(currentPoint);
  }

  /**
   * Renders the fractal on the canvas by iterating the amount of times given by
   * {@link #getIterations()}.
   */
  public void render() {
    getCanvas().clear();
    currentPoint = new Vector(0, 0);
    iterate(getIterations());
  }
}
