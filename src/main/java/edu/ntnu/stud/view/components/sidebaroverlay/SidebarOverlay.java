package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.view.utils.AnimationHandler;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

/**
 * The element that the buttons and the sidebar lay on top of, so that both can be moved together
 * when showing/hiding the sidebar.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class SidebarOverlay extends StackPane {

  private final @NotNull AnimationHandler sidebarAnimation = new AnimationHandler();
  private final @NotNull Sidebar sidebar = new Sidebar();
  private final @NotNull DoubleProperty sidebarOffset = new SimpleDoubleProperty(0);
  private final @NotNull ButtonGroup buttonGroup = new ButtonGroup();

  /**
   * Creates a new instance with a sidebar and a button group.
   */
  public SidebarOverlay() {
    super();

    sidebar.translateXProperty().bind(
        sidebar.widthProperty().subtract(sidebarOffset)
    );

    buttonGroup.translateXProperty().bind(
        sidebarOffset.negate()
    );

    StackPane.setAlignment(sidebar, Pos.TOP_RIGHT);
    StackPane.setAlignment(buttonGroup, Pos.TOP_RIGHT);

    getChildren().addAll(buttonGroup, sidebar);
  }

  /**
   * Animates the opening and closing of the sidebar.
   *
   * @param open the state to animate to
   */
  public void setState(boolean open) {
    final int fps = 40;

    sidebarAnimation.replaceAnimation(
        new Transition(fps) {
          {
            setCycleDuration(javafx.util.Duration.seconds(0.5));
            setCycleCount(1);
          }

          protected void interpolate(double frac) {
            double completion = open ? frac : 1 - frac;
            sidebarOffset.set(sidebar.getWidth() * completion);
          }
        }
    );
  }

  /**
   * Returns the sidebar node.
   *
   * @return the sidebar
   */
  public @NotNull Sidebar getSidebar() {
    return sidebar;
  }

  /**
   * Returns the button group node.
   *
   * @return the button group
   */
  public @NotNull ButtonGroup getButtonGroup() {
    return buttonGroup;
  }
}
