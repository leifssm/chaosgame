package edu.ntnu.stud.utils;

import edu.ntnu.stud.controller.InputParser;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class that debounces a function call. Waits far a given delay before calling the function. If
 * the function is called again before the delay is over, the delay is reset.
 *
 * @version 1.1
 * @author Leif Mørstad
 */
public class Debouncer {
  /**
   * The function to debounce, null if no initial function was set.
   */
  private final @Nullable Runnable function;
  private final @NotNull PauseTransition delay;
  /**
   * Creates a new instance with the given function.
   *
   * @param function the function to debounce
   * @param delay the delay in milliseconds
   */
  public Debouncer(@Nullable Runnable function, Duration delay) {
    this.function = function;
    this.delay = new PauseTransition(delay);
  }

  /**
   * Creates a new instance with the given delay and no initial function. If this constructor is
   * used, any future use of the {@link #run()} method will throw an error.
   * @param delay the delay in milliseconds
   */
  public Debouncer(@NotNull Duration delay) {
    this(null, delay);
  }

  /**
   * Creates a new instance with no initial function. If this constructor is used, any future use of
   * the {@link #run()} method will throw an error.
   */
  public Debouncer() {
    this(null, Duration.seconds(1));
  }

  /**
   * Runs the function after a delay. If the function is called again before the delay is over, the
   * delay is reset. Overrides the current debounced function for this iteration.
   *
   * @param function the function to debounce
   * @return a runnable that can be used to cancel the debounced function
   */
  public Runnable run(@NotNull Runnable function) {
    delay.setOnFinished(event -> function.run());
    delay.playFromStart();
    return delay::stop;
  }

  /**
   * Runs the set function after a delay.
   *
   * @return a runnable that can be used to cancel the debounced function
   * @throws IllegalStateException if no function was set
   * @see #run(Runnable)
   */
  public Runnable run() {
    if (function == null) {
      throw new IllegalStateException("No function was set");
    }
    return run(function);
  }
}
