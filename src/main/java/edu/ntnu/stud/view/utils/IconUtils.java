package edu.ntnu.stud.view.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Utility class for handling icons.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class IconUtils {

  /**
   * Converts the icon to the correct format.
   *
   * @param iconName The icon name to extend.
   * @return The given name prefixed by "mdi-".
   * @see <a href="https://kordamp.org/ikonli/cheat-sheet-materialdesign.html">
   * Material Design Icons</a>
   */
  public static @NotNull String extendIconName(@NotNull String iconName) {
    return "mdi-" + iconName;
  }
}
