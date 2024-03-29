package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.SimpleMatrix;
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
  private final AffineTransformation coordsToIndicesTransformation;

  public ChaosGameCanvas(int width, int height, Vector minCoords, Vector maxCoords) {
    this.width = width;
    this.height = height;
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.canvas = new int[height][width];
    clear();

    int x0Bounds = width - 1;
    int x1Bounds = height - 1;
    this.coordsToIndicesTransformation = new AffineTransformation(
        new SimpleMatrix(
            x1Bounds / (minCoords.getX1() - maxCoords.getX1()),
            0,
            0,
            x0Bounds / (maxCoords.getX0() - minCoords.getX0())
        ),
        new Vector(
            x1Bounds * maxCoords.getX1() / (maxCoords.getX1() - minCoords.getX1()),
            x0Bounds * minCoords.getX0() / (minCoords.getX0() - maxCoords.getX0())
        )
    );
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
    if (
        x < 0 ||
        x >= width ||
        y < 0 ||
        y >= height
    ) {
      return;
    }
    canvas[height - y - 1][x] = value;
  }

  public void setPixel(int x, int y) {
    setPixel(x, y, 1);
  }

  public void drawAtCoords(Vector coords) {
    Vector indices = coordsToIndicesTransformation.transform(coords);
    setPixel((int) indices.getX0(), (int) indices.getX1());
  }

  public int[][] getCanvas() {
    return canvas;
  }

  public String asString() {
    StringBuilder sb = new StringBuilder();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        sb.append(canvas[y][x] == 0 ? " " : "x");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
