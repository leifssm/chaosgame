package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.Vector;

/**
 * A canvas for drawing and getting the output of a chaos game.
 *
 * @author Leif Mørstad
 * @version 0.1
 */
public class ChaosGameCanvas {
  private final int[][] canvas;
  private final int width;
  private final int height;
  private final Vector minCoords;
  private final Vector maxCoords;
  private final AffineTransformation transformCoordsToIndices;

  public ChaosGameCanvas(int width, int height, Vector minCoords, Vector maxCoords) {
    this.width = width;
    this.height = height;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.canvas = new int[height][width];
    clear();
    this.transformCoordsToIndices = new AffineTransformation();
  }

  public void clear() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        canvas[y][x] = 0;
      }
    }
  }

  public int getPixel(int x, int y) {
    return canvas[y][x];
  }

  public void setPixel(int x, int y, int value) {
    canvas[y][x] = value;
  }

  public int[][] getCanvas() {
    return canvas;
  }
}
