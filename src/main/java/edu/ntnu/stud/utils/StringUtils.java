package edu.ntnu.stud.utils;

import org.jetbrains.annotations.NotNull;

/**
 * A class containing utility methods for working with strings.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class StringUtils {

  /**
   * Pads the given string with spaces to the left until it is of length n.
   *
   * @param s the string to pad
   * @param n the length of the resulting string
   * @return the padded string
   */
  public static @NotNull String padLeft(@NotNull String s, int n) {
    return String.format("%1$" + n + "s", s);
  }

  /**
   * Pads the given string with spaces to the right until it is of length n.
   *
   * @param s the string to pad
   * @param n the length of the resulting string
   * @return the padded string
   */
  public static @NotNull String padRight(@NotNull String s, int n) {
    return String.format("%1$" + n + "s", s);
  }
}
