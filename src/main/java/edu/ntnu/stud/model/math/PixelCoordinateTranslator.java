package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

/**
 * A class for translating between pixel coordinates and indices in a canvas.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class PixelCoordinateTranslator {

  /**
   * The transformation used to convert coordinates to indices in the canvas.
   */
  private final AffineTransformation coordsToIndicesTransformation;
  /**
   * The transformation in part used to convert indices to coordinates in the canvas.
   */
  private final SimpleMatrix indicesToCoordsScalar;
  /**
   * The translation in part used to convert indices to coordinates in the canvas.
   */
  private final Vector indicesToCoordsTranslation;

  /**
   * Creates a new instance with the given width, height, and the coordinate bounds of the fractal.
   *
   * @param width     the width of the canvas, cannot be less than 1
   * @param height    the height of the canvas, cannot be less than 1
   * @param minCoords the bottom left bounds of the fractal to show
   * @param maxCoords the top right bounds of the fractal to show
   */
  public PixelCoordinateTranslator(
      int width,
      int height,
      @NotNull Vector minCoords,
      @NotNull Vector maxCoords
  ) {
    // Does not rotate or flip the output, as this is expected to be handled elsewhere
    double x0Scalar = (width - 1) / (maxCoords.getX0() - minCoords.getX0());
    double x1Scalar = (height - 1) / (maxCoords.getX1() - minCoords.getX1());

    Vector coordsTranslation = new Vector(
        minCoords.getX0() * x0Scalar,
        minCoords.getX1() * x1Scalar
    );

    this.coordsToIndicesTransformation = new AffineTransformation(
        new SimpleMatrix(
            x0Scalar, 0,
            0, x1Scalar
        ),
        coordsTranslation.multiply(-1)
    );

    boolean sizeError = width <= 1 || height <= 1;
    this.indicesToCoordsScalar = sizeError
        ? new SimpleMatrix(0, 0, 0, 0)
        : new SimpleMatrix(
            1 / x0Scalar, 0,
            0, 1 / x1Scalar
        );

    this.indicesToCoordsTranslation = coordsTranslation;
  }

  /**
   * Transforms the given coordinates to indices in the canvas.
   *
   * @param coords the coordinates to transform
   * @return the indices in the canvas
   */
  public @NotNull IndexPair coordsToIndices(@NotNull Vector coords) {
    Vector indices = coordsToIndicesTransformation.transform(coords);
    return new IndexPair(
        (int) Math.floor(indices.getX0()),
        (int) Math.floor(indices.getX1())
    );
  }

  /**
   * Transforms the given indices to coordinates in the canvas.
   *
   * @param x0 the first index to transform
   * @param x1 the second index to transform
   * @return the coordinates in the canvas
   */
  public @NotNull Vector indicesToCoords(int x0, int x1) {
    return indicesToCoordsScalar.transform(
        new Vector(x0, x1).add(indicesToCoordsTranslation)
    );
  }

  /**
   * A record for holding a pair of indices. Used instead of a vector because the indexes are
   * integers.
   *
   * @param x0 the first index
   * @param x1 the second index
   */
  public record IndexPair(int x0, int x1) {

  }
}
