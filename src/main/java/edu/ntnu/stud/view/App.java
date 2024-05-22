package edu.ntnu.stud.view;

import edu.ntnu.stud.view.components.FractalPane;
import edu.ntnu.stud.view.components.LoaderOverlay;
import edu.ntnu.stud.view.components.sidebaroverlay.SidebarOverlay;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The main component. Contains the sidebar, loader spinner and the fractal pane.
 *
 * @author Leif Mørstad
 * @version 2.0
 */
public class App extends StackPane {

  private final @NotNull LoaderOverlay spinner = new LoaderOverlay();
  private final @NotNull SidebarOverlay sidebarOverlay = new SidebarOverlay();
  private @Nullable FractalPane currentPane;

  /**
   * Creates a new application view.
   */
  public App() {
    super();
    setMargin(spinner, new Insets(10, 10, 10, 10));
    getChildren().addAll(sidebarOverlay, spinner);
  }

  /**
   * Returns the loader spinner.
   *
   * @return the loader spinner
   */
  public @NotNull LoaderOverlay getSpinner() {
    return spinner;
  }

  /**
   * Returns the sidebar overlay.
   *
   * @return the sidebar overlay
   */
  public @NotNull SidebarOverlay getSidebarOverlay() {
    return sidebarOverlay;
  }

  /**
   * Replaces the current fractal pane with a new one.
   *
   * @param newPanel the new fractal pane
   */
  public void replaceChaosPanel(@Nullable FractalPane newPanel) {
    getChildren().remove(currentPane);
    currentPane = newPanel;
    if (newPanel == null) {
      return;
    }
    getChildren().add(newPanel);
    newPanel.toBack();
  }
}
