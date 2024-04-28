package edu.ntnu.stud.view.components.SidebarOverlay;

import edu.ntnu.stud.view.components.ComponentUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox implements ComponentUtils {
  public Sidebar() {
    super();
    useStylesheet("components/sidebar");
    getChildren().add(new Label("Sidebar Yippie"));
  }
}
