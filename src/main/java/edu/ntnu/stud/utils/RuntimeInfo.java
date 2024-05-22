package edu.ntnu.stud.utils;

/**
 * A class to store global important, unchanging variables. Not to be confused with the
 * {@link StateManager} which stores the state of the application.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class RuntimeInfo {

  private static boolean runningJavaFx = false;

  /**
   * Returns whether the application is running in a JavaFX environment.
   */
  public static boolean isGUI() {
    return runningJavaFx;
  }

  /**
   * Returns whether the application is running in a CLI environment.
   */
  public static boolean isCLI() {
    return !runningJavaFx;
  }

  /**
   * Flags that the application is running in a JavaFX environment. Should only be called at the
   * start of the application.
   */
  public static void setIsRunningJavaFx() {
    runningJavaFx = true;
  }
}
