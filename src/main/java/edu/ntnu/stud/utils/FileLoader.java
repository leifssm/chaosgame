package edu.ntnu.stud.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;

/**
 * Utility class for loading files.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class FileLoader {

  private FileLoader() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Checks if a file exists.
   *
   * @param path the path to the file
   * @return true if the file exists
   */
  public static boolean fileExists(String path) {
    URL file = FileLoader.class.getResource("../../../../" + path);
    return new File(
        String.valueOf(file)
    ).exists();
  }

  /**
   * Gets a stylesheet from resources/stylesheets/*.
   *
   * @param stylesheet the location of the stylesheet from stylesheets/ , without the file
   *                   extension
   * @return the URL of the stylesheet or null if the stylesheet could not be found
   */
  public static @Nullable String getStylesheet(@NotNull String stylesheet) {
    return getFile(
        "stylesheets",
        stylesheet,
        ".css",
        "Could not find stylesheet"
    );
  }

  /**
   * Gets the path to a file from resources/.
   *
   * @param parentFolders the parent folders of the file
   * @param path          the path to the file
   * @param fileType      the file type
   * @param errorMessage  the error message to print if the file could not be found
   * @return the path to the file or null if the file could not be found
   */
  private static @Nullable String getFile(
      @NotNull String parentFolders,
      @NotNull String path,
      @NotNull String fileType,
      @NotNull String errorMessage
  ) {
    String filePath = parentFolders + "/" + path + fileType;
    URL file = FileLoader.class.getResource("../../../../" + filePath);

    if (file == null) {
      URL triedFile = FileLoader.class.getResource(".");
      System.out.println(errorMessage + ": " + triedFile + filePath);
      return null;
    }

    return file.toExternalForm();
  }

  /**
   * Gets an image from resources/images/*.
   *
   * @param image the location and name of the image from images/
   * @return the URL of the image or null if the image could not be found
   */
  public static @Nullable String getImage(@NotNull String image) {
    return getFile(
        "images",
        image,
        "",
        "Could not find image"
    );
  }

  public static File @NotNull [] getAllFractals() {
    File fractalFolder = new File("src/main/resources/fractals/");
    try {
      var ignored = fractalFolder.createNewFile();
    } catch (Exception e) {
      System.out.println("Could not create fractals folder");
      return new File[0];
    }

    File[] files = fractalFolder.listFiles((dir, name) -> name.endsWith(".txt"));
    if (files == null || files.length == 0) {
      System.out.println("No files found");
      return new File[0];
    }
    return files;
  }
}
