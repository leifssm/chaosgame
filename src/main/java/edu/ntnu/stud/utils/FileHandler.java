package edu.ntnu.stud.utils;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.ntnu.stud.view.components.FractalPane;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Utility class for handling files and filenames.
 *
 * @author Leif Mørstad
 * @version 2.0
 */
public class FileHandler {
  private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());

  /**
   * Gets a file from resources/*.
   *
   * @param path the path to the file from resources/*
   * @return the file
   */
  public static @NotNull File getFile(@NotNull String path) {
    return new File("src/main/resources/" + path);
  }

  /**
   * Converts a file to a URL.
   *
   * @param file the file to convert
   * @return the URL of the file
   */
  public static @NotNull URL convertFileToUrl(@NotNull File file) {
    try {
      return file.toURI().toURL();
    } catch (MalformedURLException e) {
      // Runtime exception is used because this should never happen unless the error is in the code
      throw new RuntimeException("Error in file handling: " + e);
    }
  }

  /**
   * Gets the URL of a file from resources/*.
   *
   * @param path the path to the file from resources/*
   * @return the URL of the file
   */
  public static @NotNull URL getAbsoluteFileUrl(@NotNull String path) {
    return convertFileToUrl(getFile(path));
  }

  /**
   * Gets the path to a file from resources/.
   *
   * @param path the path to the file
   * @return the path to the file
   */
  public static @NotNull String getAbsoluteFilePath(@NotNull String path) {
    return getFile(path).getAbsolutePath();
  }

  /**
   * Gets the path to a file from resources/*.
   *
   * @param path         the path to the file
   * @param errorMessage the error message to print if the file could not be found
   * @return the path to the file or null if the file could not be found
   */
  public static @Nullable String getAbsoluteFilePathIfExists(
      @NotNull String path,
      @NotNull String errorMessage
  ) {
    File file = getFile(path);
    if (!file.exists()) {
      LOGGER.severe(errorMessage + ": " + file);
      return null;
    }
    return file.getAbsolutePath();
  }

  /**
   * Gets a resource from resources/*, used when the caller does not have permission to use the
   * {@link File} class to use files.
   *
   * @param path         the path to the resource from resources/*
   * @param errorMessage the error message to print if the resource could not be found
   * @return the path of the resource or null if the resource could not be found
   */
  public static @Nullable String getResource(
      @NotNull String path,
      @NotNull String errorMessage
  ) {
    URL url = FractalPane.class.getResource("../../../../../" + path);
    if (url == null) {
      LOGGER.severe(errorMessage + ": " + getAbsoluteFilePath(path));
      return null;
    }
    return url.toExternalForm();
  }

  /**
   * Gets the url to a file from resources/*.
   *
   * @param path         the path to the file
   * @param errorMessage the error message to print if the file could not be found
   * @return the url to the file or null if the file could not be found
   */
  public static @Nullable URL getAbsoluteFileUrlIfExists(
      @NotNull String path,
      @NotNull String errorMessage
  ) {
    File file = getFile(path);
    if (!file.exists()) {
      LOGGER.severe(errorMessage + ": " + file);
      return null;
    }
    return convertFileToUrl(file);
  }

  /**
   * Gets a stylesheet from resources/stylesheets/*.
   *
   * @param stylesheet the location of the stylesheet from stylesheets/*, without the file extension
   * @return the URL of the stylesheet or null if the stylesheet could not be found
   */
  public static @Nullable String getStylesheet(@NotNull String stylesheet) {
    return getResource(
        "stylesheets/" + removeFileExtension(stylesheet) + ".css",
        "Could not find stylesheet"
    );
  }

  /**
   * Gets an image from resources/images/*.
   *
   * @param image the location and extension of the image from images/
   * @return the URL of the image or null if the image could not be found
   */
  public static @Nullable String getImage(@NotNull String image) {
    return getResource(
        "images/" + image,
        "Could not find image"
    );
  }

  /**
   * Gets a JSON file from resources/*.
   *
   * @param json the location and name of the json file from resources/
   * @return the URL of the json file, or null if the file could not be found
   */
  public static @Nullable URL getJson(@NotNull String json) {
    return getAbsoluteFileUrlIfExists(
        removeFileExtension(json) + ".json",
        "Could not find json file"
    );
  }

  /**
   * Reads a JSON file and passes it through a callback function before returning it.
   *
   * @param path     the path to the JSON file
   * @param callback the callback function to pass the JSON through
   * @param <T>      the type of the result
   * @return the result of the callback function
   */
  public static <T> @Nullable T readFile(@NotNull String path, Callback<JsonNode, T> callback) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode tree = mapper.readTree(getJson(path));
      return callback.call(tree);
    } catch (Exception e) {
      LOGGER.severe("Could not read " + path);
      return null;
    }
  }

  /**
   * Reads a JSON file and passes it through a callback function before returning it.
   *
   * @param file     the JSON file
   * @param callback the callback function to pass the JSON through
   * @param <T>      the type of the result
   * @return the result of the callback function
   */
  public static <T> @Nullable T readFile(@NotNull File file, Callback<JsonNode, T> callback) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      JsonNode tree = mapper.readTree(file);
      return callback.call(tree);
    } catch (Exception e) {
      LOGGER.severe("Could not read " + file.getAbsolutePath());
      return null;
    }
  }

  public static @NotNull String removeFileExtension(@NotNull String fileName) {
    // https://stackoverflow.com/questions/924394/how-to-get-the-filename-without-the-extension-in-java
    return fileName.replaceFirst("\\.[^/\\\\]+$", "");
  }

  /**
   * Reads a JSON file and returns the JSON node.
   *
   * @param path the path to the JSON file
   * @return the JSON node
   */
  public static @Nullable JsonNode readFile(@NotNull String path) {
    return readFile(path, json -> json);
  }

  /**
   * Reads a file and returns the JSON node.
   *
   * @param path the JSON file
   * @return the JSON node
   */
  public static @Nullable JsonNode readFile(@NotNull File path) {
    return readFile(path, json -> json);
  }

  /**
   * Writes an object to a file.
   *
   * @param path   the path to the file
   * @param object the object to write
   * @return true if the object was successfully written to the file, false otherwise
   */
  public static boolean writeToFile(@NotNull String path, @NotNull Object object) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      File file = getFile(path);
      writer.writeValue(file, object);
      LOGGER.info("Successfully wrote to " + path);
      return true;
    } catch (Exception e) {
      LOGGER.severe("Could not write to " + path);
      return false;
    }
  }
}
