package edu.ntnu.stud.view.components;

import edu.ntnu.stud.utils.UsageFlagger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LoaderOverlay extends ProgressIndicator {
  private @Nullable UsageFlagger handler = null;
  public LoaderOverlay() {
    super();
    setMaxSize(100, 100);
    StackPane.setAlignment(this, Pos.BOTTOM_LEFT);
    setProgress(-1);
    setLoading(false);
  }

  private void setLoading(boolean loading) {
    setVisible(loading);
  }

  public void setHandler(@NotNull UsageFlagger handler) {
    if (this.handler != null) {
      this.handler.unsubscribe(this::setLoading);
    }
    this.handler = handler;
    handler.subscribe(this::setLoading);
  }
}
