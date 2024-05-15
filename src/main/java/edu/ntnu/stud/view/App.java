package edu.ntnu.stud.view;

import edu.ntnu.stud.view.components.FractalPane;
import edu.ntnu.stud.view.components.LoaderOverlay;
import edu.ntnu.stud.view.components.sidebaroverlay.SidebarOverlay;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The main application view. Contains the sidebar, loader spinner and the fractal pane.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class App extends StackPane {

  private final @NotNull LoaderOverlay spinner = new LoaderOverlay();

  /**
   * Creates a new application view.
   */
  public App() {
    super();
    setMargin(spinner, new Insets(10, 10, 10, 10));
    SidebarOverlay sidebarOverlay = new SidebarOverlay();
    getChildren().addAll(sidebarOverlay, spinner);
  }

  /**
   * Returns the loader spinner.
   */
  public @NotNull LoaderOverlay getSpinner() {
    return spinner;
  }

  public void replaceChaosPanel(@Nullable FractalPane oldPanel, @NotNull FractalPane newPanel) {
    if (oldPanel != null) {
      oldPanel.destroy();
      getChildren().remove(oldPanel);
    }
    getChildren().add(newPanel);
    newPanel.toBack();
  }
}
