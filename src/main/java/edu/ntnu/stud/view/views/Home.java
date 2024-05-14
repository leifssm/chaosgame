package edu.ntnu.stud.view.views;

import edu.ntnu.stud.utils.Debouncer;
import edu.ntnu.stud.view.components.ChaosGamePanel;
import edu.ntnu.stud.view.components.sidebaroverlay.SidebarOverlay;
import javafx.scene.layout.StackPane;

import javafx.util.Duration;

public class Home extends StackPane {
  private int width = 1;
  private int height = 1;
  private ChaosGamePanel chaosGamePanel;
  private final Debouncer sizeUpdateDebouncer = new Debouncer(this::updateSize, Duration.millis(100));
  public Home() {
    super();
    SidebarOverlay sidebarOverlay = new SidebarOverlay();
    getChildren().add(sidebarOverlay);
    widthProperty().subscribe(w -> setWidth(w.intValue()));
    heightProperty().subscribe(h -> setHeight(h.intValue()));
  }

  private void setWidth(int width) {
    if (width == this.width || width <= 1) {
      return;
    }
    this.width = width;
    sizeUpdateDebouncer.run();
  }

  private void setHeight(int height) {
    if (height == this.height || height <= 1) {
      return;
    }
    this.height = height;
    sizeUpdateDebouncer.run();
  }

  /**
   * Only run through debouncer.
   */
  private void updateSize() {
    System.out.println("Updating size to " + width + "x" + height);
    if (chaosGamePanel != null) {
      chaosGamePanel.disconnect();
      getChildren().remove(chaosGamePanel);
    }
    chaosGamePanel = new ChaosGamePanel(width, height);
    getChildren().add(chaosGamePanel);
    chaosGamePanel.toBack();

  }
}
