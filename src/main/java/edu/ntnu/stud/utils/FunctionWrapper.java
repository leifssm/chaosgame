package edu.ntnu.stud.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Abstract class for any wrapper class, wrapping a function. The wrapper class running the function
 * can cancel the running of the function.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public abstract class FunctionWrapper {
  /**
   * Interface for cancelling a task.
   */
  public interface TaskCanceller {
    /**
     * Cancels the task if it has not already been called.
     */
    void cancel();
  }
  /**
   * Notifies the wrapper to run the function.
   *
   * @return a canceller for the task
   */
  public abstract @NotNull TaskCanceller run();
}
