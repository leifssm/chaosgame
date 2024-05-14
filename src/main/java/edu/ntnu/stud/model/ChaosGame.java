package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.TransformationGroup;
import edu.ntnu.stud.utils.GlobalData;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing all needed information concerning the running and displaying of a fractal
 * display.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public abstract class ChaosGame {
  /**
   * The canvas on which the fractal is drawn.
   */
  private final @NotNull ChaosGameCanvas canvas;

  /**
   * The transformations used to generate the fractal.
   */
  private final @NotNull TransformationGroup transformations;

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
        description.maxCoords(),
        GlobalData.isGUI()
    );
  }

  public @NotNull TransformationGroup getTransformations() {
    return transformations;
  }

  public @NotNull ChaosGameCanvas getCanvas() {
    return canvas;
  }

  public abstract void render();
}
