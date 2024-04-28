package edu.ntnu.stud.view.components.SidebarOverlay;

import edu.ntnu.stud.view.utils.AnimationHandler;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

public class SidebarOverlay extends StackPane {
  private final @NotNull AnimationHandler sidebarAnimation = new AnimationHandler();
  private final @NotNull Sidebar sidebar = new Sidebar();
  private final @NotNull DoubleProperty sidebarOffset = new SimpleDoubleProperty(0);
  private boolean sidebarOpen = false;
  public SidebarOverlay() {
    super();

    ButtonGroup buttonGroup = new ButtonGroup(
        new ActionButton("folder", "folder", this::toggleSidebar).setType("primary"),
        new ActionButton("account", "account", () -> System.out.println("Button 2 clicked"))
    );

    sidebar.translateXProperty().bind(
        sidebar.widthProperty().subtract(sidebarOffset)
    );

    buttonGroup.translateXProperty().bind(
        sidebarOffset.negate()
    );

    StackPane.setAlignment(sidebar, Pos.TOP_RIGHT);
    StackPane.setAlignment(buttonGroup, Pos.TOP_RIGHT);

    getChildren().addAll(sidebar, buttonGroup);
  }

  private void toggleSidebar() {
    final boolean open = sidebarOpen = !sidebarOpen;
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
}
