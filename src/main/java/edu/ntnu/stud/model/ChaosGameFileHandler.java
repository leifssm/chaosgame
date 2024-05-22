package edu.ntnu.stud.model;

import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.stud.model.math.*;
import edu.ntnu.stud.utils.FileHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.util.ArrayList;

/**
 * This class is responsible for reading and writing chaos game descriptions to and from files.
 *
 * @author Leif Mørstad
 * @version 1.2
 * @see ChaosGameDescription
 */
public class ChaosGameFileHandler {

  public static @NotNull File @NotNull [] getAllFractals() {
    File fractalFolder = FileHandler.getFile("fractals/");
    try {
      var ignored = fractalFolder.createNewFile();
    } catch (Exception e) {
      System.out.println("Could not create fractals folder");
      return new File[0];
    }

    File[] files = fractalFolder.listFiles((dir, name) -> name.endsWith(".json"));
    if (files == null || files.length == 0) {
      System.out.println("No files found");
      return new File[0];
    }
    return files;
  }

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
    return readChaosGame(tree);
  }

  public static @NotNull ChaosGameDescription readChaosGame(
      @Nullable JsonNode node
  ) throws InvalidObjectException {
    if (node == null) {
      throw new InvalidObjectException("Invalid chaos game description");
    }
    var minCoords = readVector(node.get("minCoords"));
    var maxCoords = readVector(node.get("maxCoords"));
    var transformations = readTransformations(node.get("transformations"));

    return new ChaosGameDescription(
        minCoords,
        maxCoords,
        transformations
    );
  }

  private static double getValidDouble(
      @NotNull String fieldName,
      @Nullable JsonNode node
  ) throws InvalidObjectException {
    if (node == null) {
      throw new InvalidObjectException("Missing field: " + fieldName);
    }
    double value = node.get(fieldName).asDouble();
    if (Double.isNaN(value)) {
      throw new InvalidObjectException("Invalid value for field: " + fieldName);
    }
    return value;
  }

  private static @NotNull Vector readVector(@Nullable JsonNode node) throws InvalidObjectException {
    if (node == null) {
      throw new InvalidObjectException("Missing vector field");
    }
    double x0 = getValidDouble("x0", node);
    double x1 = getValidDouble("x1", node);
    return new Vector(x0, x1);
  }

  private static @NotNull SimpleMatrix readMatrix(
      @Nullable JsonNode node
  ) throws InvalidObjectException {
    if (node == null) {
      throw new InvalidObjectException("Missing matrix field");
    }
    double a00 = getValidDouble("a00", node);
    double a01 = getValidDouble("a01", node);
    double a10 = getValidDouble("a10", node);
    double a11 = getValidDouble("a11", node);
    return new SimpleMatrix(a00, a01, a10, a11);
  }

  private static @NotNull TransformationGroup readTransformations(
      @Nullable JsonNode node
  ) throws InvalidObjectException {
    if (node == null) {
      throw new InvalidObjectException("Missing transformations field");
    }
    if (!node.isArray()) {
      throw new InvalidObjectException("Transformations must be an array");
    }
    ArrayList<Transform2D> transformations = new ArrayList<>();

    for (JsonNode transformation : node) {
      if (!transformation.has("type")) {
        throw new InvalidObjectException("All transformations must have a 'type' field");
      }
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
      @Nullable JsonNode transformation,
      @NotNull ArrayList<Transform2D> transformations
  ) throws InvalidObjectException {
    if (transformation == null) {
      throw new InvalidObjectException("Missing affine transformation");
    }
    SimpleMatrix matrix = readMatrix(transformation.get("matrix"));
    Vector translation = readVector(transformation.get("translation"));
    transformations.add(new AffineTransformation(matrix, translation));
  }

  private static void handleJuliaTransformation(
      @Nullable JsonNode transformation,
      @NotNull ArrayList<Transform2D> transformations
  ) throws InvalidObjectException {
    if (transformation == null) {
      throw new InvalidObjectException("Missing julia transformation");
    }
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

  /**
   * Writes a chaos game description to a file under resources/fractals/*.
   *
   * @param filename    the name of the file to write to
   * @param description the chaos game description to write to the file
   */
  public static void writeToFile(
      @NotNull String filename,
      @NotNull ChaosGameDescription description
  ) {
    FileHandler.writeToFile(
        "fractals/" + filename,
        description
    );
  }
}
