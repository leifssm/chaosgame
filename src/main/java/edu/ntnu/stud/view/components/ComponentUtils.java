package edu.ntnu.stud.view.components;

import edu.ntnu.stud.utils.FileLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A utility interface for JavaFX components.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public interface ComponentUtils {

  /**
   * Throws if the class it is implemented on is not of type Parent
   *
   * @see Parent
   */
  private void checkThis() {
    if (!(this instanceof Parent)) {
      throw new UnsupportedOperationException(
          "ComponentUtils can only be implemented on Parent nodes"
      );
    }
  }

  /**
   * Adds a stylesheet to the given element, and provides shorthand for adding classes to it.
   * If no classes are given, the stylesheet name is used as a class. If null is passed as classname
   * no classes are added.
   *
   * @param element the element to add the stylesheet to
   * @param stylesheet the name of the stylesheet to add
   * @param classes the classes to add to the element, if given an empty array the stylesheet name
   *                is used as a class. If null, no classes are be added
   */
  default void useStylesheet(
      @NotNull Parent element,
      @NotNull String stylesheet,
      String @Nullable ... classes
  ) {
    checkThis();
    String filePath = FileLoader.getStylesheet(stylesheet);
    if (filePath == null) {
      return;
    }
    element.getStylesheets().add(filePath);

    if (classes == null) {
      return;
    }

    if (classes.length > 0) {
      addCSSClasses(classes);
      return;
    }
    String[] files = stylesheet.split("/");
    String className = files[files.length - 1];

    addCSSClasses(element, className);
  }

  /**
   * Adds a stylesheet to the element, and provides shorthand for adding classes to it.
   *
   * @param stylesheet the name of the stylesheet to add
   * @param classes the classes to add to the element
   */
  default void useStylesheet(@NotNull String stylesheet, String @NotNull ... classes) {
    checkThis();
    useStylesheet((Parent) this, stylesheet, classes);
  }

  /**
   * Adds a stylesheet to the element, and provides option to use stylesheet name as a classname.
   *
   * @param element the element to add the stylesheet to
   * @param stylesheet the name of the stylesheet to add
   * @param noClasses whether to add classes to the element, if true no classes are added
   */
  default void useStylesheet(@NotNull Parent element, @NotNull String stylesheet, boolean noClasses) {
    if (noClasses) {
      useStylesheet(element, stylesheet, (String) null);
    } else {
      useStylesheet(element, stylesheet);
    }
  }

  /**
   * Shorthand for adding a stylesheet to the 'this' element.
   *
   * @param stylesheet the name of the stylesheet to add
   * @param noClasses whether to add classes to the element, if true no classes are added
   * @see #useStylesheet(Parent, String, boolean)
   */
  default void useStylesheet(@NotNull String stylesheet, boolean noClasses) {
    checkThis();
    useStylesheet((Parent) this, stylesheet, noClasses);
  }

  /**
   * Adds the given classes to the element.
   *
   * @param element the element to add the classes to
   * @param classNames the classes to add
   */
  default void addCSSClasses(@NotNull Parent element, @NotNull String @NotNull ... classNames) {
    element.getStyleClass().addAll(classNames);
  }

  /**
   * Shorthand for adding the given classes to the 'this' element.
   *
   * @param classNames the classes to add to the element
   * @see #addCSSClasses(Parent, String...)
   */
  default void addCSSClasses(@NotNull String @NotNull ... classNames) {
    checkThis();
    addCSSClasses((Parent) this, classNames);
  }

  /**
   * Removes the given classes from the element.
   *
   * @param element the element to remove the classes from
   * @param classNames the classes to remove
   */
  default void removeCSSClasses(@NotNull Parent element, @NotNull String @NotNull ... classNames) {
    element.getStyleClass().removeAll(classNames);
  }

  /**
   * Shorthand for removing the given classes from the 'this' element.
   *
   * @param classNames the classes to remove from the element
   * @see #removeCSSClasses(Parent, String...)
   */
  default void removeCSSClasses(@NotNull String @NotNull ... classNames) {
    checkThis();
    removeCSSClasses((Parent) this, classNames);
  }

  /**
   * Checks if the element has the given class.
   *
   * @param element the element to check
   * @param className the class to check for
   * @return whether the element has the class
   */
  default boolean hasCSSClass(@NotNull Parent element, @NotNull String className) {
    return element.getStyleClass().contains(className);
  }

  /**
   * Shorthand for checking if the 'this' element has the given class.
   *
   * @param className the class to check for
   * @return whether the element has the class
   * @see #hasCSSClass(Parent, String)
   */
  default boolean hasCSSClass(@NotNull String className) {
    checkThis();
    return hasCSSClass((Parent) this, className);
  }

  /**
   * Toggles the given class on the element.
   *
   * @param element the element to toggle the class on
   * @param className the class to toggle
   * @return whether the class was added
   */
  default boolean toggleCSSClass(@NotNull Parent element, @NotNull String className) {
    if (hasCSSClass(className)) {
      element.getStyleClass().remove(className);
      return false;
    } else {
      element.getStyleClass().add(className);
      return true;
    }
  }

  /**
   * Shorthand for toggling the given class on the 'this' element.
   *
   * @param className the class to toggle
   * @return whether the class was added
   * @see #toggleCSSClass(Parent, String)
   */
  default boolean toggleCSSClass(@NotNull String className) {
    checkThis();
    return toggleCSSClass((Parent) this, className);
  }
}