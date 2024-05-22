package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.view.components.ComponentUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

/**
 * A sidebar that displays a list of fractals to choose between.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class Sidebar extends StackPane implements ComponentUtils {
  private final @NotNull GridPane gridPane = new GridPane();
  private final @NotNull ScrollPane scrollPane = new ScrollPane(new AnchorPane(gridPane));
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
    gridPane.getColumnConstraints().addAll(column1, column2);

    getChildren().addAll(scrollPane);
    clear();
  }

  /**
   * Adds a fractal to the sidebar.
   *
   * @param fractalName the name of the fractal
   * @param onClick     the action to run when the fractal is clicked
   */
  public void addFractalDisplay(String fractalName, Runnable onClick) {
    int x = gridIndex % 2;
    int y = gridIndex / 2;

    gridPane.add(new FractalDisplay(fractalName, onClick), x, y);
    gridIndex++;
  }

  /**
   * Clears the sidebar of all fractals.
   */
  public void clear() {
    gridPane.getChildren().clear();
    gridIndex = 0;
  }
}
