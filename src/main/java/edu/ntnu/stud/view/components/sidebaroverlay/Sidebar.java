package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.view.components.ComponentUtils;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * A sidebar that displays a list of fractals to choose between.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class Sidebar extends GridPane implements ComponentUtils {
  private int gridIndex = 0;

  /**
   * Creates a new instance with stored fractals.
   */
  public Sidebar() {
    super();
    useStylesheet("components/sidebar");

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(50);
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(50);
    getColumnConstraints().addAll(column1, column2);
  }

  public void addFractalDisplay(String fractalName, Runnable onClick) {
    int x = gridIndex % 2;
    int y = gridIndex / 2;

    add(new FractalDisplay(fractalName, onClick), x, y);
    gridIndex++;
  }

  public void clear() {
    getChildren().clear();
    gridIndex = 0;
  }
}
