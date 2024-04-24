package edu.ntnu.stud.view.utils;

import org.jetbrains.annotations.NotNull;

public class IconUtils {
  /**
   * Extends the icon name to the new Material Design Icons 2 format.
   *
   * @param iconName The icon name to extend.
   * @return The given name prefixed by "mdi2" followed by the first letter of the name, a dash and
   *         then the icon name.
   */
  public static @NotNull String extendIconName(@NotNull String iconName) {
    return "mdi2" + iconName.charAt(0) + "-" + iconName;
  }
}
