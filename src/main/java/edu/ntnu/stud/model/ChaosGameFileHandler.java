package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * This class is responsible for reading and writing chaos game descriptions to and from files.
 *
 * @author Leif Mørstad
 * @version 0.1
 * @see ChaosGameDescription
 */
public class ChaosGameFileHandler {
  public static ChaosGameDescription readFromFile(@NotNull String filename) {
    Scanner scanner;
    try {
      File file = new File("src/main/resources/fractals/" + filename);
      if (!file.exists()) {
        throw new Exception();
      }
      scanner = new Scanner(file);
    } catch (Exception e) {
      throw new IllegalArgumentException("Could not read file: " + filename);
    }

    scanner
        .useLocale(Locale.ENGLISH)
        .useDelimiter("\\s*(#[^\\n]*)?\\n");

    String type = scanner.next();
    double[] minCoords = readNumberLine(scanner.next(), 2);
    double[] maxCoords = readNumberLine(scanner.next(), 2);

    TransformationGroup transformations = switch (type) {
      case "Affine2D" -> readAffineTransformation(scanner);
      case "Julia" -> readJuliaTransformation(scanner);
      default -> throw new IllegalArgumentException("Unknown fractal type: " + type);
    };

    return new ChaosGameDescription(
        new Vector(minCoords),
        new Vector(maxCoords),
        transformations
    );
  }

  private static double @NotNull [] readNumberLine(@NotNull String line, int expectedLength) {
    if (expectedLength < 1) {
      throw new IllegalArgumentException("Expected length must be at least 1");
    }
    Scanner scanner = new Scanner(line)
        .useLocale(Locale.ENGLISH)
        .useDelimiter("\\s*,\\s*|\\s*(?!\\d)");

    double[] numbers = new double[expectedLength];

    for (int i = 0; i < expectedLength; i++) {
      if (!scanner.hasNextDouble()) {
        throw new IllegalArgumentException(
            "Expected "
                + expectedLength
                + " numbers, but only found "
                + i
                + " number"
                + (i != 1 ? "s": "")
                + " in line: \""
                + line
                + '"'
        );
      }
      numbers[i] = scanner.nextDouble();
    }

    return numbers;
  }

  @Contract("_ -> new")
  private static @NotNull TransformationGroup readAffineTransformation(@NotNull Scanner scanner) {
    ArrayList<Transform2D> transformations = new ArrayList<>();

    while (scanner.hasNext()) {
      double[] parts = readNumberLine(scanner.next(), 6);
      transformations.add(new AffineTransformation(parts));
    }

    return new TransformationGroup(transformations);
  }

  private static @NotNull TransformationGroup readJuliaTransformation(@NotNull Scanner scanner) {
    double[] complexNumbers = readNumberLine(scanner.next(), 2);
    ComplexNumber complexNumber = new ComplexNumber(complexNumbers);
    return new TransformationGroup(
        new JuliaTransformation(complexNumber, true),
        new JuliaTransformation(complexNumber, false)
    );
  }

  public static void writeToFile(String filename, ChaosGameDescription description) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
