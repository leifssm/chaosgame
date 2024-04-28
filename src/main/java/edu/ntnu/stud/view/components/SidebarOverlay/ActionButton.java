package edu.ntnu.stud.view.components.SidebarOverlay;

import edu.ntnu.stud.view.components.ComponentUtils;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import org.jetbrains.annotations.NotNull;

/**
 * A simple button with an icon and a description that shows when hovered.
 *
 * @author Leif Mørstad
 * @version 0.1
 * @see <a href="https://kordamp.org/ikonli/cheat-sheet-materialdesign.html">
 *        Material Design Icons
 *      </a>
 */
public class ActionButton extends Button implements ComponentUtils {

  /**
   * Creates a new instance with the given icon, description and action.
   *
   * @param icon the name of the icon to use
   * @param description the description to show when hovered
   * @param action the action to run when clicked
   * @see <a href="https://kordamp.org/ikonli/cheat-sheet-materialdesign.html">
   *  *        Material Design Icons
   *  *      </a>
   */
  public ActionButton(@NotNull String icon, @NotNull String description, @NotNull Runnable action) {
    super();
    setTooltip(new Tooltip(description));
//    setGraphic(new FontIcon("mdi-" + icon));
    setOnAction(event -> action.run());
    addCSSClasses("action-button");
  }

  /**
   * Sets the type of the button.
   *
   * @param type the new type of the button
   * @return this instance
   */
  public ActionButton setType(@NotNull String type) {
    addCSSClasses(type);
    removeCSSClasses("primary"); // TODO add all types (or use enum)
    return this;
  }
}