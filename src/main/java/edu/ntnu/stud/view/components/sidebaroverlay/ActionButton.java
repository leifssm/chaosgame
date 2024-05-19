package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.view.components.StandardButton;
import edu.ntnu.stud.view.utils.IconUtils;
import javafx.scene.control.Tooltip;
import org.jetbrains.annotations.NotNull;

/**
 * A simple button with an icon and a description that shows when hovered.
 *
 * @author Leif Mørstad
 * @version 0.2
 * @see <a href="https://kordamp.org/ikonli/cheat-sheet-materialdesign.html">
 * Material Design Icons
 * </a>
 */
public class ActionButton extends StandardButton {

  /**
   * Creates a new instance with the given icon, description and action.
   *
   * @param icon        the name of the icon to use
   * @param description the description to show when hovered
   * @param action      the action to run when clicked
   * @see <a href="https://kordamp.org/ikonli/cheat-sheet-materialdesign.html">
   * Material Design Icons</a>
   */
  public ActionButton(@NotNull String icon, @NotNull String description, @NotNull Runnable action) {
    super("", action);
    setTooltip(new Tooltip(description));
    setGraphic(IconUtils.createIcon(icon));
    addCssClasses("action-button");
  }
}