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
public class IconFactory {

  /**
   * Converts the icon to the correct format.
   *
   * @param iconName The icon name to extend.
   * @return The given name prefixed by "mdi-".
   * @see <a href="https://kordamp.org/ikonli/cheat-sheet-materialdesign.html">
   * Material Design Icons</a>
   */
  private static @NotNull String extendIconName(@NotNull String iconName) {
    return "mdi-" + iconName;
  }

  /**
   * Creates a new icon with the given icon id.
   *
   * @param iconId the name of the icon to create
   * @return the created icon
   */
  public static @NotNull FontIcon create(@NotNull String iconId) {
    FontIcon icon = new FontIcon(extendIconName(iconId));
    icon.setIconColor(Paint.valueOf("#ffffff"));
    return icon;
  }
}
