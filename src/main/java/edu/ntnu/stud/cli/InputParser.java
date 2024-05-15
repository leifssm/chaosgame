package edu.ntnu.stud.cli;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>InputParser.</h1>
 * <p>A singleton class with lazy instantiation that manages and parses user input.</p>
 * <br>
 * <h2>Role and Responsibility:</h2>
 * <p>
 * This class is responsible for parsing and validating user input. It is a singleton class with the
 * option to {@link #initialize} the scanner with a given input stream for testing purposes. It has
 * methods for getting integers, floats, characters, strings, booleans and LocalTimes from the user.
 * It has the option for extra validation through either regexes, patterns or functions, and has the
 * option of giving specialized error messages.
 * </p>
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class InputParser {

  /**
   * The scanner object that is used to read inputs from. If the singleton isn't yet initialized,
   * the scanner is null. If a get method is called before the class is initialized, it will
   * automatically initialize the scanner with the current {@link System#in}.
   */
  private static Scanner scanner;

  /**
   * If initialized, it closes the {@link InputStream} the singleton is initialized with, and clears
   * the connected scanner from the class.
   */
  public static void close() {
    if (InputParser.scanner == null) {
      return;
    }
    scanner.close();
    scanner = null;
  }

  /**
   * Shorthand for {@link InputParser#getString(String, String, String)}. Defaults the error message
   * to "The string does not match the criteria".
   *
   * @see InputParser#getString(String, String, String)
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NonNls @NotNull @RegExp String regex
  ) throws IllegalArgumentException {
    // Defaults the error message
    return getString(prompt, regex, "The string does not match the criteria");
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that matches the given
   * regex. If the string doesn't match, the error message is shown if it is not null, and the user
   * is prompted again.
   *
   * @param prompt       The prompt to show the user.
   * @param regex        The regex to match the input against.
   * @param errorMessage The error message to show the user if the input doesn't match the regex.
   *                     Pass {@code null} to not show an error message.
   * @return A string given by the user that matches the regex.
   * @throws IllegalArgumentException If the prompt or the regex is null.
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull @RegExp String regex,
      @Nullable String errorMessage
  ) throws IllegalArgumentException {
    // Compiles a regex into a pattern and calls the associated getString method.
    final Pattern pattern = Pattern.compile(regex);
    return getString(prompt, pattern, errorMessage);
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that matches the given
   * pattern. If the string doesn't match, the error message is shown if it is not null, and the
   * user is prompted again.
   *
   * @param prompt       The prompt to show the user.
   * @param pattern      The pattern to match the input against.
   * @param errorMessage The error message to show the user if the input doesn't match the pattern.
   *                     Pass {@code null} to not show an error message.
   * @return A string given by the user that matches the pattern.
   * @throws IllegalArgumentException If the prompt or the pattern is null.
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull Pattern pattern,
      String errorMessage
  ) throws IllegalArgumentException {
    Matcher result;

    // Gets a string from the user until it matches the pattern.
    while (true) {
      result = pattern.matcher(getString(prompt));
      if (result.matches()) {
        return result.group();
      }

      // Prints the error message if it is set.
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  /**
   * Prompts the user and return the given string.
   *
   * @param prompt The prompt to show the user.
   * @return The string the user entered.
   * @throws IllegalStateException If the prompt is null.
   */
  public static @NotNull String getString(@NotNull String prompt) throws IllegalStateException {
    System.out.print(prompt + ": ");
    return getString();
  }

  /**
   * Gets a string from the user without a prompt, and returns the given string.
   *
   * @return The string the user entered.
   */
  public static @NotNull String getString() {
    initialize();
    return scanner.nextLine();
  }

  /**
   * Initializes the scanner with {@link System#in}. Does not override an already initialized
   * scanner.
   *
   * @see System#in
   */
  public static void initialize() {
    initialize(System.in);
  }

  /**
   * Initializes the scanner with a given non-null {@link InputStream input stream}. Does not
   * override the scanner if it is already initialized.
   *
   * @param stream The input stream to read data from.
   * @throws IllegalArgumentException If the stream is null
   */
  public static void initialize(@NotNull InputStream stream) throws IllegalArgumentException {
    if (isInitialized()) {
      return;
    }
    scanner = new Scanner(stream, StandardCharsets.UTF_8);
  }

  /**
   * Returns if the scanner has yet been initialized with {@link #initialize}.
   *
   * @return If the scanner is initialized.
   */
  public static boolean isInitialized() {
    return scanner != null;
  }

  /**
   * Shorthand for {@link InputParser#getString(String, Pattern, String)}. Defaults the error
   * message to "The string does not match the criteria".
   *
   * @see InputParser#getString(String, Pattern, String)
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull Pattern pattern
  ) throws IllegalArgumentException {
    // Defaults the error message
    return getString(prompt, pattern, "The string does not match the criteria");
  }

  /**
   * Shorthand for {@link InputParser#getString(String, InputValidator, String)}. Defaults the error
   * message to "The string does not match the criteria".
   *
   * @see InputParser#getString(String, InputValidator, String)
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull InputValidator<String> validator
  ) throws IllegalArgumentException {
    // Defaults the error message
    return getString(prompt, validator, "The string does not match the criteria");
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that passes the
   * validation. If the string does not match with the given validation, and the error message is
   * not null, it is shown to the user.
   *
   * @param prompt       The prompt to show the user.
   * @param validator    The validator function to test the string with.
   * @param errorMessage The error message to show the user if the string doesn't pass validation.
   *                     Pass {@code null} to not show an error message.
   * @return A string given by the user that matches the validation.
   * @throws IllegalArgumentException If the prompt or validator function is null.
   */
  public static @NotNull String getString(
      @NotNull String prompt,
      @NotNull InputValidator<String> validator,
      @Nullable String errorMessage
  ) throws IllegalArgumentException {
    String result;
    while (true) {
      // Gets a string from the user until it matches the validation.
      result = getString(prompt);
      if (validator.test(result)) {
        return result;
      }

      // Prints the error message if it is set.
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  /**
   * Shorthand for {@link InputParser#getInt(String, InputValidator, String)}. Defaults the error
   * message to "The number does not match the criteria".
   *
   * @see InputParser#getInt(String, InputValidator, String)
   */
  public static int getInt(
      @NotNull String prompt,
      @NotNull InputValidator<Integer> validator
  ) throws IllegalArgumentException {
    // Defaults the error message
    return getInt(prompt, validator, "The number does not match the criteria");
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * an integer that passes the validation. If the string can't be converted, the error message "The
   * value is not a valid number" is shown and the user is prompted again. If the integer does not
   * match with the given validation, and the error message is not null, it is shown to the user.
   *
   * @param prompt       The prompt to show the user.
   * @param validator    The validator function to test the integer with.
   * @param errorMessage The error message to show the user if the integer doesn't pass validation.
   *                     Pass {@code null} to not show an error message.
   * @return An integer given by the user that matches the validation.
   * @throws IllegalArgumentException If the prompt or validator function is null.
   */
  public static int getInt(
      @NotNull String prompt,
      @NotNull InputValidator<Integer> validator,
      @Nullable String errorMessage
  ) throws IllegalArgumentException {
    int result;
    while (true) {
      // Gets an int from the user until it matches the validation.
      result = getInt(prompt);
      if (validator.test(result)) {
        return result;
      }

      // Prints the error message if it is set.
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * an int. If the string can't be converted, the error message "The value is not a valid number"
   * is shown and the user is prompted again.
   *
   * @param prompt The prompt to show the user.
   * @return An int given by the user.
   * @throws IllegalArgumentException If the prompt is null.
   */
  public static int getInt(@NotNull String prompt) throws IllegalArgumentException {
    // Stores the string to a variable so that the value can be used in the catch block also.
    String string = "";
    final Pattern pattern = Pattern.compile("^-?[0-9]+$");

    while (true) {
      try {
        // Gets an input from the user until it can be parsed as an int.
        string = getString(prompt);
        return Integer.parseInt(string);
      } catch (NumberFormatException error) {
        // If the input matches the pattern, the string must be a valid number, but still threw,
        // so it must be causing an integer overflow.
        if (pattern.matcher(string).matches()) {
          System.out.println(
              "The number has to be between -2147483648 and 2147483647"
          );
        } else {
          System.out.println("The value is not a valid number");
        }
      }
    }
  }

  /**
   * Shorthand for {@link InputParser#getFloat(String, InputValidator, String)}. Defaults the error
   * message to "The decimal number does not fit the criteria".
   *
   * @see InputParser#getFloat(String, InputValidator, String)
   */
  public static float getFloat(
      @NotNull String prompt,
      @NotNull InputValidator<Float> validator
  ) throws IllegalArgumentException {
    // Defaults the error message
    return getFloat(prompt, validator, "The decimal number does not fit the criteria");
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * a float that passes the validation. If the string can't be converted, the error message "The
   * value is not a valid decimal number" is shown and the user is prompted again. If the float does
   * not match with the given validation, and the error message is not null, it is shown to the
   * user.
   *
   * @param prompt       The prompt to show the user.
   * @param validator    The validator function to test the float with.
   * @param errorMessage The error message to show the user if the float doesn't pass validation.
   *                     Pass {@code null} to not show an error message.
   * @return A float given by the user that matches the validation.
   * @throws IllegalArgumentException If the prompt or validator function is null.
   */
  public static float getFloat(
      @NotNull String prompt,
      @NotNull InputValidator<Float> validator,
      @Nullable String errorMessage
  ) throws IllegalArgumentException {
    float result;
    while (true) {
      // Gets a float from the user until it matches the validation.
      result = getFloat(prompt);
      if (validator.test(result)) {
        return result;
      }

      // Prints the error message if it is set.
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * a float. If the string can't be converted, the error message "The value is not a valid decimal
   * number" is shown and the user is prompted again.
   *
   * @param prompt The prompt to show the user.
   * @return A float given by the user.
   * @throws IllegalArgumentException If the prompt is null.
   */
  public static float getFloat(@NotNull String prompt) throws IllegalArgumentException {
    while (true) {
      // Gets a string from the user until it can be parsed as a float.
      try {
        return Float.parseFloat(getString(prompt));
      } catch (NumberFormatException ignored) {
        System.out.println("The value is not a valid decimal number");
      }
    }
  }

  /**
   * Shorthand for {@link InputParser#getChar(String, InputValidator, String)}. Defaults the error
   * message to "The character does not fit the criteria".
   *
   * @see InputParser#getChar(String, InputValidator, String)
   */
  public static char getChar(
      @NotNull String prompt,
      @NotNull InputValidator<Character> validator
  ) throws IllegalArgumentException {
    // Defaults the error message
    return getChar(prompt, validator, "The character does not fit the criteria");
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * a character that passes the validation. If the string can't be converted, the error message
   * "You must give only one character" is shown and the user is prompted again. If the character
   * does not match with the given validation, and the error message is not null, it is shown to the
   * user.
   *
   * @param prompt       The prompt to show the user.
   * @param validator    The validator function to test the character with.
   * @param errorMessage The error message to show the user if the character doesn't pass
   *                     validation. Pass {@code null} to not show an error message.
   * @return A character given by the user that matches the validation.
   * @throws IllegalArgumentException If the prompt or validator function is null.
   */
  public static char getChar(
      @NotNull String prompt,
      @NotNull InputValidator<Character> validator,
      @Nullable String errorMessage
  ) throws IllegalArgumentException {
    char input;
    while (true) {
      // Gets a char from the user until it matches the validation.
      input = getChar(prompt);
      if (validator.test(input)) {
        return input;
      }

      // Prints the error message if it is set.
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * a character. If the string can't be converted, the error message "You must give only one
   * character" is shown and the user is prompted again.
   *
   * @param prompt The prompt to show the user.
   * @return A character given by the user.
   * @throws IllegalArgumentException If the prompt is null.
   */
  public static char getChar(@NotNull String prompt) throws IllegalArgumentException {
    // Gets a string from the user until it matches the regex of one character.
    final Pattern pattern = Pattern.compile("^.$");
    final String input = getString(prompt, pattern, "You must give only one character");
    return input.charAt(0);
  }

  /**
   * Shorthand for {@link InputParser#getTime(String, InputValidator, String)}. Defaults the error
   * message to "The time does not fit the criteria".
   *
   * @see InputParser#getTime(String, InputValidator, String)
   */
  public static @NotNull LocalTime getTime(
      @NotNull String prompt,
      @NotNull InputValidator<LocalTime> validator
  ) throws IllegalArgumentException {
    // Defaults the error message
    return getTime(prompt, validator, "The time does not fit the criteria");
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * a time that passes the validation. If the string can't be converted, the error message "Please
   * write the time in the format of "h:mm" or "hh:mm"" or "The time is not valid" is shown and the
   * user is prompted again. If the time does not match with the given validation, and the error
   * message is not null, it is shown to the user.
   *
   * @param prompt       The prompt to show the user.
   * @param validator    The validator function to test the time with.
   * @param errorMessage The error message to show the user if the time doesn't pass validation.
   *                     Pass {@code null} to not show an error message.
   * @return A time given by the user that matches the validation.
   * @throws IllegalArgumentException If the prompt or validator function is null.
   */
  public static @NotNull LocalTime getTime(
      @NotNull String prompt,
      @NotNull InputValidator<LocalTime> validator,
      @Nullable String errorMessage
  ) throws IllegalArgumentException {
    LocalTime result;
    while (true) {
      // Gets a time from the user until it matches the validation.
      result = getTime(prompt);
      if (validator.test(result)) {
        return result;
      }

      // Prints the error message if it is set.
      if (errorMessage != null) {
        System.out.println(errorMessage);
      }
    }
  }

  /**
   * Prompts the user with the given prompt until the user enters a string that can be converted to
   * a time. If the string can't be converted, the error message "Please write the time in the
   * format of "h:mm" or "hh:mm"" or "The time is not valid" is shown and the user is prompted
   * again.
   *
   * @param prompt The prompt to show the user.
   * @return A time given by the user.
   * @throws IllegalArgumentException If the prompt is null.
   */
  public static @NotNull LocalTime getTime(@NotNull String prompt) throws IllegalArgumentException {
    final Pattern pattern = Pattern.compile("^[0-9]{1,2}:[0-9]{1,2}$");
    String result;
    while (true) {
      // Gets a string from the user until it matches a time regex
      result = getString(
          prompt + " [h:mm / hh:mm]",
          pattern,
          "Please write the time in the format of \"h:mm\" or \"hh:mm\""
      );

      // If only one hour digit is given, it adds a zero for convenience
      if (result.length() == 4) {
        result = "0" + result;
      }

      try {
        // Tries parsing the string as a time, and loops if it fails
        return LocalTime.parse(result);
      } catch (DateTimeParseException ignored) {
        System.out.println("The time is not valid");
      }
    }
  }

  /**
   * Prompts the user with the given prompt until the user enters either y or n, and converts it to
   * a boolean. If the string can't be converted, the error message "Please only use one of the
   * given characters" is shown and the user is prompted again.
   *
   * @param prompt The prompt to show the user.
   * @return A boolean given by the user.
   * @throws IllegalArgumentException If the prompt is null.
   */
  public static boolean getBoolean(@NotNull String prompt) throws IllegalArgumentException {
    // Gets a character from the user until it is either y or n, and converts it to a boolean.
    return getString(
        prompt + " [y/n]",
        "^[ynYN]$",
        "Please only use one of the given characters"
    ).equalsIgnoreCase("y");
  }

  /**
   * Prompts the user with the given prompt until the user enters either y or n, and converts it to
   * a boolean. If the user does not enter anything the default value is implied. If the string
   * can't be converted, the error message "Please only use one of the given characters" is shown
   * and the user is prompted again.
   *
   * @param prompt The prompt to show the user.
   * @return A boolean that is given by the user of defaulted.
   * @throws IllegalArgumentException If the prompt is null.
   */
  public static boolean getBoolean(
      @NotNull String prompt,
      boolean defaultValue
  ) throws IllegalArgumentException {
    // Gets a string from the user until it is either y, n or an empty string
    final String result = getString(
        prompt + (defaultValue ? " [Y/n]" : " [y/N]"),
        "^[ynYN]?$",
        "Please only use one of the given characters"
    );

    // If the string is empty the default value is returned
    if (result.isEmpty()) {
      return defaultValue;
    }
    return result.equalsIgnoreCase("y");
  }

  /**
   * Shorthand for {@link InputParser#waitForUser(String)}. Defaults the prompt message to "Press
   * enter to continue".
   *
   * @see #waitForUser()
   */
  public static void waitForUser() {
    // Waits for the user to enter any string.
    waitForUser("Press enter to continue");
  }

  /**
   * Prompts the user to press enter, and stalls the program until the user does so.
   *
   * @param prompt The prompt to show the user.
   * @throws IllegalArgumentException If the prompt is null.
   * @see #waitForUser()
   */
  public static void waitForUser(@NotNull String prompt) throws IllegalArgumentException {
    // Waits for the user to enter any string.
    System.out.println("\n" + prompt);
    getString();
  }

  /**
   * A functional interface for validating user input, so that a specialized function interface has
   * to be implemented for all types.
   *
   * @param <T> The expected type of input to validate.
   */
  public interface InputValidator<T> {

    boolean test(@NotNull T input);
  }
}