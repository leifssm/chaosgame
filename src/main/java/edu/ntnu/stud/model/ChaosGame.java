package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.Transform2D;
import edu.ntnu.stud.model.math.TransformationGroup;
import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing all needed information concerning the running and displaying of a fractal display
 *
 * @author Leif Mørstad
 * @version 0.1
 */
public class ChaosGame {
  private final ChaosGameCanvas canvas;
  private final TransformationGroup transformations;
  private Vector currentPoint = new Vector(0, 0);

  public ChaosGame(int width, int height, @NotNull ChaosGameDescription description) {
    this.transformations = description.transformations();
    this.canvas = new ChaosGameCanvas(
        width,
        height,
        description.minCoords(),
        description.maxCoords()
    );
    canvas.drawAtCoords(currentPoint);
  }

  public TransformationGroup getTransformations() {
    return transformations;
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
    currentPoint = transformations.transform(currentPoint);
    canvas.drawAtCoords(currentPoint);
  }
}
