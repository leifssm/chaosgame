package edu.ntnu.stud.utils;

import edu.ntnu.stud.controller.InputParser;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class that debounces a function call. Waits far a given delay before calling the function. If
 * the function is called again before the delay is over, the delay is reset.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class Debouncer {
  /**
   * The function to debounce, null if no initial function was set.
   */
  private final @Nullable Runnable function;
  /**
   * The task that is scheduled to run the function.
   */
  private TimerTask task;

  /**
   * The timer that schedules the tasks.
   *
   * @see <a href="https://stackoverflow.com/questions/68572061/how-to-prevent-a-timer-from-running-in-the-background">Used Stack Overflow</a>
   */
  private final Timer timer = new Timer(true);

  /**
   * Creates a new instance with the given function.
   *
   * @param function the function to debounce
   */
  public Debouncer(@NotNull Runnable function) {
    this.function = function;
  }

  /**
   * Creates a new instance with no initial function. If this constructor is used, any future use of
   * the {@link #run()} method will throw an error.
   */
  public Debouncer() {
    this.function = null;
  }

  /**
   * Runs the function after a delay. If the function is called again before the delay is over, the
   * delay is reset. Overrides the current debounced function for this iteration.
   *
   * @param function the function to debounce
   * @return a runnable that can be used to cancel the debounced function
   */
  public Runnable run(Runnable function) {
    if (task != null) {
      task.cancel();
    }

    task = new TimerTask() {
      @Override
      public void run() {
        function.run();
      }
    };
    timer.schedule(task, 1000);
    return task::cancel;
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
