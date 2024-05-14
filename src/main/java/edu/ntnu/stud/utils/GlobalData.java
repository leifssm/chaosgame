package edu.ntnu.stud.utils;

public class GlobalData {
  private static boolean runningJavaFx = false;

  public static boolean isGUI() {
    return runningJavaFx;
  }
  public static boolean isCLI() {
    return !runningJavaFx;
  }
  public static void setIsRunningJavaFx() {
    runningJavaFx = true;
  }
}
