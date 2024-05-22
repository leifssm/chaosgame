package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.view.components.ComponentUtils;
import edu.ntnu.stud.view.components.StandardButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * A group of buttons. Displays them from the bottom right towards the top right.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ButtonGroup extends HBox implements ComponentUtils {

  private final VBox buttonWrapper = new VBox();

  /**
   * Creates a new instance with the given buttons in reverse order.
   *
   * @param buttons the buttons to add
   */
  public ButtonGroup(@NotNull StandardButton @NotNull ... buttons) {
    super();
    useStylesheet("components/button-group");
    addCssClasses(buttonWrapper, "button-wrapper");

    for (StandardButton button : buttons) {
      addButton(button);
    }
    getChildren().add(buttonWrapper);
  }

  /**
   * Adds a button to the group. Adds it to the top of the group.
   *
   * @param button the button to add
   */
  public @NotNull ButtonGroup addButton(@NotNull StandardButton button) {
    buttonWrapper.getChildren().addFirst(button);
    return this;
  }
}
