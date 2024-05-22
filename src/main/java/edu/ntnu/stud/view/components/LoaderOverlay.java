package edu.ntnu.stud.view.components;

import edu.ntnu.stud.utils.UsageFlagger;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A simple overlay that shows a loading spinner when loading.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class LoaderOverlay extends ProgressIndicator {

  private @Nullable UsageFlagger handler = null;

  /**
   * Creates a new unbound overlay.
   */
  public LoaderOverlay() {
    super();
    setMaxSize(50, 50);
    StackPane.setAlignment(this, Pos.BOTTOM_LEFT);
    setProgress(-1);
    setLoading(false);
  }

  /**
   * Shows/hides the loading spinner.
   *
   * @param loading whether to show the spinner
   */
  private void setLoading(boolean loading) {
    setVisible(loading);
  }

  /**
   * Sets the handler to listen for loading events, and detached the previous one.
   *
   * @param handler the new handler
   */
  public void setHandler(@NotNull UsageFlagger handler) {
    if (this.handler != null) {
      this.handler.unsubscribe(this::setLoading);
    }
    this.handler = handler;
    handler.subscribe(this::setLoading);
  }
}
