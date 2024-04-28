package edu.ntnu.stud.view.views;

import edu.ntnu.stud.view.components.ChaosGamePanel;
import edu.ntnu.stud.view.components.SidebarOverlay.SidebarOverlay;
import javafx.scene.layout.StackPane;

import java.util.concurrent.atomic.AtomicInteger;

public class Home extends StackPane {
  private volatile int width = 1;
  private volatile int height = 1;
  private ChaosGamePanel chaosGamePanel;
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
    updateSize();
  }

  private void setHeight(int height) {
    if (height == this.height || height <= 1) {
      return;
    }
    this.height = height;
    updateSize();
  }

  private synchronized void updateSize() {
    if (chaosGamePanel != null) {
      chaosGamePanel.disconnect();
    }
    getChildren().remove(chaosGamePanel);
    chaosGamePanel = new ChaosGamePanel(width, height);
    getChildren().add(chaosGamePanel);
    chaosGamePanel.toBack();

  }
}
