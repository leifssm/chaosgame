package edu.ntnu.stud.view;

import edu.ntnu.stud.view.components.FractalPane;
import edu.ntnu.stud.view.components.LoaderOverlay;
import edu.ntnu.stud.view.components.sidebaroverlay.SidebarOverlay;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class App extends StackPane {
  private final @NotNull LoaderOverlay spinner = new LoaderOverlay();
  public App() {
    super();
    setMargin(spinner, new Insets(10, 10, 10, 10));
    SidebarOverlay sidebarOverlay = new SidebarOverlay();
    getChildren().addAll(sidebarOverlay, spinner);
  }

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
