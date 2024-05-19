package edu.ntnu.stud.view.utils;

import javafx.scene.paint.Paint;
import org.jetbrains.annotations.NotNull;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Utility class for handling icons.
 *
 * @author Leif Mørstad
 * @version 1.1
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

  public static @NotNull FontIcon createIcon(@NotNull String iconName) {
    FontIcon icon = new FontIcon(extendIconName(iconName));
    icon.setIconColor(Paint.valueOf("#ffffff"));
    return icon;
  }
}
