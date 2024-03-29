package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * A class containing all needed information concerning the running and displaying of a fractal display
 *
 * @author Leif Mørstad
 * @version 0.1
 */
public class ChaosGame {
  private final ChaosGameCanvas canvas;
  private final ChaosGameDescription description;
  private Vector currentPoint = new Vector(0, 0);

  public ChaosGame(int width, int height, @NotNull ChaosGameDescription description) {
    this.description = description;
    this.canvas = new ChaosGameCanvas(
        width,
        height,
        description.getMinCoords(),
        description.getMaxCoords()
    );
    canvas.drawAtCoords(currentPoint);
  }

  public ChaosGameDescription getDescription() {
    return description;
  }

  public ChaosGameCanvas getCanvas() {
    return canvas;
  }

  public void iterate(int steps) {
    for (int i = 0; i < steps; i++) {
      iterate();
    }
  }

  public void iterate() {
    currentPoint = description.getTransformations().transform(currentPoint);
    canvas.drawAtCoords(currentPoint);
  }
}
