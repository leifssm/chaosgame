package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.SimpleMatrix;
import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

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
  private final AffineTransformation coordsToIndicesTransformation;

  public ChaosGameCanvas(int width, int height, @NotNull Vector minCoords, @NotNull Vector maxCoords) {
    this.width = width;
    this.height = height;
    this.canvas = new int[height][width];
    clear();

    double x0Scalar = (width - 1) / (maxCoords.getX0() - minCoords.getX0());
    double x1Scalar = (height - 1) / (maxCoords.getX1() - minCoords.getX1());
    this.coordsToIndicesTransformation = new AffineTransformation(
        new SimpleMatrix(
            x0Scalar, 0,
            0, x1Scalar
        ),
        new Vector(
            - minCoords.getX0() * x0Scalar,
            - minCoords.getX1() * x1Scalar
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
    return canvas[height - y - 1][x];
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

  public void setPixel(double x, double y) {
    if (x < 0 || y < 0) return;
    setPixel((int) x, (int) y);
  }

  public void drawAtCoords(Vector coords) {
    Vector scaled = coordsToIndicesTransformation.transform(coords);
    setPixel(scaled.getX0(), scaled.getX1());
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
