package edu.ntnu.stud.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URL;

public class FileLoader {
  private FileLoader() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean fileExists(String path) {
    URL file = FileLoader.class.getResource("../../../../" + path);
    return new File(
        String.valueOf(file)
    ).exists();
  }

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

  public static @Nullable String getStylesheet(@NotNull String stylesheet) {
    return getFile(
        "stylesheets",
        stylesheet,
        ".css",
        "Could not find stylesheet"
    );
  }

  public static @Nullable String getImage(@NotNull String image) {
    return getFile(
        "images",
        image,
        "",
        "Could not find image"
    );
  }

}
