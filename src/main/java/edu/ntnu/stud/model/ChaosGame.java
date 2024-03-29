package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.Vector;

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
  private final Vector currentPoint = new Vector(0, 0);
  private final Random random = new Random();

  public ChaosGame(ChaosGameDescription description, int width, int height) {
    this.description = description;
    this.canvas = new ChaosGameCanvas(
        width,
        height,
        description.getMinCoords(),
        description.getMaxCoords()
    );
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

  }
}
