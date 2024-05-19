package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing all needed information concerning the running and displaying of a fractal
 * display.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class IterativeChaosGame extends ChaosGame {

  /**
   * The scalar for the number of iterations to perform.
   */
  private static final int ITERATION_SCALAR = 1;
  /**
   * The current point where the fractal is drawn from. Starts at (0, 0)
   */
  private @NotNull Vector currentPoint = new Vector(0, 0);

  /**
   * @see ChaosGame#ChaosGame(int, int, ChaosGameDescription)
   */
  public IterativeChaosGame(
      int width,
      int height,
      @NotNull ChaosGameDescription description
  ) throws IllegalArgumentException {
    super(width, height, description);
  }

  /**
   * Renders the fractal by iterating the transformations a number of times proportional to the
   * screen size.
   */
  public void render() {
    iterate(getIterations());
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
}
