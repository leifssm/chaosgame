package edu.ntnu.stud.model;

import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.stud.model.math.*;
import edu.ntnu.stud.utils.FileHandler;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.util.ArrayList;

/**
 * This class is responsible for reading and writing chaos game descriptions to and from files.
 *
 * @author Leif Mørstad
 * @version 1.0
 * @see ChaosGameDescription
 */
public class ChaosGameFileHandler {

  /**
   * Reads a chaos game description from a file.
   *
   * @param filename the name of the file to read from
   * @return the chaos game description read from the file
   * @throws IllegalArgumentException if the file could not be read or the file contains invalid
   *                                  data
   */
  public static @NotNull ChaosGameDescription readFromFile(
      @NotNull String filename
  ) throws InvalidObjectException, FileNotFoundException {
    JsonNode tree = FileHandler.readFile("fractals/" + filename);
    if (tree == null) {
      throw new FileNotFoundException("File not found: " + filename);
    }

    var minCoords = readVector(tree.get("minCoords"));
    var maxCoords = readVector(tree.get("maxCoords"));
    var transformations = readTransformations(tree.get("transformations"));

    return new ChaosGameDescription(
        minCoords,
        maxCoords,
        transformations
    );
  }

  private static void requireFields(
      @NotNull JsonNode node,
      @NotNull String @NotNull ... fields
  ) throws InvalidObjectException {
    for (String field : fields) {
      if (!node.has(field)) {
        throw new InvalidObjectException("Missing field: " + field);
      }
    }
  }

  private static double getValidDouble(
      @NotNull String fieldName,
      @NotNull JsonNode node
  ) throws InvalidObjectException {
    requireFields(node, fieldName);
    double value = node.get(fieldName).asDouble();
    if (Double.isNaN(value)) {
      throw new InvalidObjectException("Invalid value for field: " + fieldName);
    }
    return value;
  }

  private static @NotNull Vector readVector(@NotNull JsonNode node) throws InvalidObjectException {
    double x0 = getValidDouble("x0", node);
    double x1 = getValidDouble("x1", node);
    return new Vector(x0, x1);
  }

  private static @NotNull SimpleMatrix readMatrix(
      @NotNull JsonNode node
  ) throws InvalidObjectException {
    double a00 = getValidDouble("a00", node);
    double a01 = getValidDouble("a01", node);
    double a10 = getValidDouble("a10", node);
    double a11 = getValidDouble("a11", node);
    return new SimpleMatrix(a00, a01, a10, a11);
  }

  private static @NotNull TransformationGroup readTransformations(
      @NotNull JsonNode node
  ) throws InvalidObjectException {
    if (!node.isArray()) {
      throw new InvalidObjectException("Transformations must be an array");
    }
    ArrayList<Transform2D> transformations = new ArrayList<>();

    for (JsonNode transformation : node) {
      requireFields(transformation, "type");
      String type = transformation.get("type").asText();
      switch (type) {
        case "AffineTransformation" -> handleAffineTransformation(transformation, transformations);
        case "JuliaTransformation" -> handleJuliaTransformation(transformation, transformations);
        default -> throw new IllegalArgumentException("Unknown fractal type: " + type);
      }
    }
    return new TransformationGroup(transformations);
  }

  private static void handleAffineTransformation(
      @NotNull JsonNode transformation,
      @NotNull ArrayList<Transform2D> transformations
  ) throws InvalidObjectException {
    SimpleMatrix matrix = readMatrix(transformation.get("matrix"));
    Vector translation = readVector(transformation.get("translation"));
    transformations.add(new AffineTransformation(matrix, translation));
  }

  private static void handleJuliaTransformation(
      @NotNull JsonNode transformation,
      @NotNull ArrayList<Transform2D> transformations
  ) throws InvalidObjectException {
    ComplexNumber complexNumber = ComplexNumber.fromVector(
        readVector(transformation.get("complexNumber"))
    );
    boolean exists = transformations
        .stream()
        .anyMatch(julia -> {
          if (!(julia instanceof JuliaTransformation)) {
            return false;
          }
          return ((JuliaTransformation) julia).getComplexNumber().equals(complexNumber);
        });
    if (exists) {
      return;
    }
    transformations.add(new JuliaTransformation(complexNumber, true));
    transformations.add(new JuliaTransformation(complexNumber, false));
  }

  public static void writeToFile(String filename, ChaosGameDescription description) {
    FileHandler.writeToFile(
        "fractals/" + filename,
        description
    );
  }
}
