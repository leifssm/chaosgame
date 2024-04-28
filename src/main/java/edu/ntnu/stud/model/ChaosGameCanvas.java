package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.AffineTransformation;
import edu.ntnu.stud.model.math.SimpleMatrix;
import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * A canvas for drawing and getting the output of a chaos game.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ChaosGameCanvas {
  /**
   * The canvas on which the fractal is drawn, starts filled with 0. The origin is in the bottom
   * left, with the y-axis pointing upwards and the x-axis pointing to the right, so that the
   * coordinates (0, 0) are in the bottom left corner. This only happens when the canvas is accessed
   * through any the {@link #touchPixel(int, int)} method, so don't access directly unless you know
   * what you are doing.
   */
  private final int[][] canvas;

  /**
   * The width canvas in pixels.
   */
  private final int width;

  /**
   * The height canvas in pixels.
   */
  private final int height;

  /**
   * The transformation used to convert coordinates to indices in the canvas.
   */
  private final @NotNull AffineTransformation coordsToIndicesTransformation;

  private final SubscriptionHandler<int[][]> subscriptionHandler;

  /**
   * Creates a new instance with the given width, height, and the coordinate bounds of the fractal.
   *
   * @param width the width of the canvas, cannot be less than 1
   * @param height the height of the canvas, cannot be less than 1
   * @param minCoords the minimum coordinate bounds of the fractal to show
   * @param maxCoords the maximum coordinate bounds of the fractal to show
   * @throws IllegalArgumentException if the width or height is less than 1, or if given null
   *                                  vectors as coordinates
   */
  public ChaosGameCanvas(
      int width,
      int height,
      @NotNull Vector minCoords,
      @NotNull Vector maxCoords
  ) throws IllegalArgumentException {
    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("Width and height must be at least 1 each");
    }
    this.width = width;
    this.height = height;
    this.canvas = new int[height][width];
    subscriptionHandler = new SubscriptionHandler<>(canvas);

    // Fills the array with 0s
    clear();

    // Only translates and scales the coordinates to indices without rotating or flipping, as this
    // is handled by the setPixel() and getPixel() methods
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

  /**
   * Fills the canvas with 0s.
   */
  public void clear() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        canvas[y][x] = 0;
      }
    }
    subscriptionHandler.notifySubscribers();
  }

  /**
   * Returns the pixel value at the given coordinates. The origin is in the bottom left, with the
   * coordinates (0, 0) being in the bottom left corner.
   *
   * @param x the x-coordinate of the pixel from left to right
   * @param y the y-coordinate of the pixel from bottom to top
   * @return the pixel value at the given coordinates
   * @throws ArrayIndexOutOfBoundsException if the given coordinates are outside the canvas
   */
  public int getPixel(int x, int y) throws ArrayIndexOutOfBoundsException {
    return canvas[height - y - 1][x];
  }

  /**
   * Sets the pixel value at the given coordinates. The origin is in the bottom left, with the
   * coordinates (0, 0) being in the bottom left corner. Does nothing if the given coordinates are
   * outside the canvas.
   *
   * @param x the x-coordinate of the pixel from left to right
   * @param y the y-coordinate of the pixel from bottom to top
   * @param value the value to set the pixel to
   */
  public void setPixel(int x, int y, int value) {
    if (
        x < 0
        || x >= width
        || y < 0
        || y >= height
    ) {
      return;
    }
    canvas[height - y - 1][x] = value;
    subscriptionHandler.notifySubscribers();
  }

  /**
   * Sets the pixel value at the given coordinates to 1. The origin is in the bottom left, with the
   * coordinates (0, 0) being in the bottom left corner. Does nothing if the given coordinates are
   * outside the canvas. Defaults the value to 1.
   *
   * @param x the x-coordinate of the pixel from left to right
   * @param y the y-coordinate of the pixel from bottom to top
   */
  public void touchPixel(int x, int y) {
    if (
        x < 0
            || x >= width
            || y < 0
            || y >= height
    ) {
      return;
    }
    canvas[height - y - 1][x]++;
    subscriptionHandler.notifySubscribers();
  }

  /**
   * Rounds the double down to the nearest integer to prevent -0.4 from being rounded to 0, then
   * calls {@link #touchPixel(int, int)} with the new values
   *
   * @param x the x-coordinate of the pixel from left to right
   * @param y the y-coordinate of the pixel from bottom to top
   * @see #touchPixel(int, int)
   */
  public void touchPixel(double x, double y) {
    if (x < 0 || y < 0) {
      return;
    }
    this.touchPixel((int) x, (int) y);
  }

  /**
   * Draws a pixel at the given coordinates, scaling and translating them to fit the canvas.
   *
   * @param coords the coordinates to draw the pixel at
   */
  public void drawAtCoords(Vector coords) {
    Vector scaled = coordsToIndicesTransformation.transform(coords);
    touchPixel(scaled.getX0(), scaled.getX1());
  }

  /**
   * Returns the canvas as a 2D array of integers.
   *
   * @return the canvas as a 2D array of integers
   * @see #canvas
   */
  public int[][] getCanvas() {
    return canvas;
  }

  public SubscriptionHandler<int[][]> getSubscriptionHandler() {
    return subscriptionHandler;
  }

  /**
   * Returns a simple ascii art representation of the canvas.
   *
   * @return a simple ascii art representation of the canvas
   */
  public @NotNull String asSimpleString() {
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