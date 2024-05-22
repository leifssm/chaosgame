package edu.ntnu.stud.utils;

import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * A class that debounces a function call. Waits far a given delay before calling the function. If
 * the function is called again before the delay is over, the delay is reset. Should not be used
 * outside the JavaFX thread.
 *
 * @author Leif Mørstad
 * @version 1.2
 */
public class Debouncer {
  /**
   * The function to debounce, null if no initial function was set.
   */
  private final @NotNull Runnable function;

  /**
   * A transition class used to sync the debouncing with javafx.
   */
  private final @NotNull PauseTransition delay;

  private final @NotNull SimpleBooleanProperty isWaiting = new SimpleBooleanProperty(false);

  /**
   * Creates a new instance with the given function.
   *
   * @param function the function to debounce
   * @param delay    the delay in milliseconds
   */
  public Debouncer(@NotNull Runnable function, Duration delay) {
    this.function = function;
    this.delay = new PauseTransition(delay);
  }

  /**
   * Runs the function after a delay. If the function is called again before the delay is over, the
   * delay is reset.
   */
  public void run() {
    isWaiting.set(true);
    delay.setOnFinished(event -> {
      function.run();
      isWaiting.set(false);
    });
    delay.playFromStart();
  }

  /**
   * Gets the property that is true if the debouncer is waiting for the delay to finish.
   *
   * @return a boolean property
   */
  public @NotNull ReadOnlyBooleanProperty getIsWaiting() {
    return isWaiting;
  }
}
