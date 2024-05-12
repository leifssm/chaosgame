package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.view.components.ComponentUtils;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
//import org.kordamp.ikonli.javafx.FontIcon;

public class ButtonGroup extends HBox implements ComponentUtils {
  private final VBox buttonWrapper = new VBox();
  public ButtonGroup(@NotNull ActionButton @NotNull ... buttons) {
    super();
    useStylesheet("components/button-group");
    addCssClasses(buttonWrapper, "button-wrapper");

    for (ActionButton button : buttons) {
      addButton(button);
    }
    getChildren().add(buttonWrapper);
  }

  public void addButton(@NotNull ActionButton button) {
    buttonWrapper.getChildren().addFirst(button);
  }


}
