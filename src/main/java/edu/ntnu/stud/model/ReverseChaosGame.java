package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing all needed information concerning the running and displaying of a fractal
 * display.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ReverseChaosGame extends ChaosGame {

  /**
   * Creates a new instance with the given width, height and description.
   *
   * @param width the width of the canvas
   * @param height the height of the canvas
   * @param description the description of the chaos game
   * @throws IllegalArgumentException if description is null
   */
  public ReverseChaosGame(
      int width,
      int height,
      @NotNull ChaosGameDescription description
  ) throws IllegalArgumentException {
    super(width, height, description);
  }

  static final int MAX_ITERATION = 100;

  public void renderPixel(int x0Index, int x1Index) {
    Vector plotLoc = getCanvas().getCoordinateTranslator().indicesToCoords(x0Index, x1Index);


    int iterations = 0;
    double x0Squared = 0;
    double x1Squared = 0;

    double x0 = 0;
    double x1 = 0;

    while (x0Squared + x1Squared <= 4 && iterations < MAX_ITERATION) {
      x1 = 2 * x0 * x1 + plotLoc.getX1();
      x0 = x0Squared - x1Squared + plotLoc.getX0();
      x0Squared = x0 * x0;
      x1Squared = x1 * x1;
      iterations++;
    }

    getCanvas().setPixel(x0Index, x1Index, iterations);
  }

  public void render() {
    System.out.println("Rendering");
    for (int x0Index = 0; x0Index < getCanvas().getWidth(); x0Index++) {
      for (int x1Index = 0; x1Index < getCanvas().getHeight(); x1Index++) {
        renderPixel(x0Index, x1Index);
      }
    }
    System.out.println("Done rendering");
  }
}
