package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.view.components.ComponentUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * A sidebar that displays a list of fractals to choose between.
 *
 * @author Leif Mørstad
 * @version 0.1
 */
public class Sidebar extends VBox implements ComponentUtils {

  /**
   * Creates a new instance with stored fractals.
   */
  public Sidebar() {
    super();
    useStylesheet("components/sidebar");
    getChildren().add(new Label("Sidebar Yippie"));
    // TODO
  }
}
