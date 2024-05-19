package edu.ntnu.stud.view.components;

import edu.ntnu.stud.utils.FileHandler;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A utility interface for JavaFX components.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public interface ComponentUtils {

  /**
   * Adds a stylesheet to the element, and provides shorthand for adding classes to it.
   *
   * @param stylesheet the name of the stylesheet to add
   * @param classes    the classes to add to the element
   */
  default void useStylesheet(@NotNull String stylesheet, String... classes) {
    checkThis();
    useStylesheet((Parent) this, stylesheet, classes);
  }

  /**
   * Throws if the class it is implemented on is not of type Parent.
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
   * Adds a stylesheet to the given element, and provides shorthand for adding classes to it. If no
   * classes are given, the stylesheet name is used as a class. If null is passed as classname no
   * classes are added.
   *
   * @param element    the element to add the stylesheet to
   * @param stylesheet the name of the stylesheet to add
   * @param classes    the classes to add to the element, if given an empty array the stylesheet
   *                   name is used as a class. If null, no classes are be added
   */
  default void useStylesheet(
      @NotNull Parent element,
      @NotNull String stylesheet,
      String @Nullable ... classes
  ) {
    checkThis();
    String filePath = FileHandler.getStylesheet(stylesheet);
    if (filePath == null) {
      return;
    }
    element.getStylesheets().add(filePath);

    if (classes == null) {
      return;
    }

    if (classes.length > 0) {
      addCssClasses(classes);
      return;
    }
    String[] files = stylesheet.split("/");
    String className = files[files.length - 1];

    addCssClasses(element, className);
  }

  /**
   * Shorthand for adding a stylesheet to the 'this' element.
   *
   * @param stylesheet the name of the stylesheet to add
   * @param noClasses  whether to add classes to the element, if true no classes are added
   * @see #useStylesheet(Parent, String, boolean)
   */
  default void useStylesheet(@NotNull String stylesheet, boolean noClasses) {
    checkThis();
    useStylesheet((Parent) this, stylesheet, noClasses);
  }

  /**
   * Adds a stylesheet to the element, and provides option to use stylesheet name as a classname.
   *
   * @param element    the element to add the stylesheet to
   * @param stylesheet the name of the stylesheet to add
   * @param noClasses  whether to add classes to the element, if true no classes are added
   */
  default void useStylesheet(
      @NotNull Parent element,
      @NotNull String stylesheet,
      boolean noClasses
  ) {
    if (noClasses) {
      useStylesheet(element, stylesheet, (String) null);
    } else {
      useStylesheet(element, stylesheet);
    }
  }

  /**
   * Shorthand for adding the given classes to the 'this' element.
   *
   * @param classNames the classes to add to the element
   * @see #addCssClasses(Parent, String...)
   */
  default void addCssClasses(@NotNull String @NotNull ... classNames) {
    checkThis();
    addCssClasses((Parent) this, classNames);
  }

  /**
   * Adds the given classes to the element.
   *
   * @param element    the element to add the classes to
   * @param classNames the classes to add
   */
  default void addCssClasses(@NotNull Parent element, @NotNull String @NotNull ... classNames) {
    element.getStyleClass().addAll(classNames);
  }

  /**
   * Shorthand for removing the given classes from the 'this' element.
   *
   * @param classNames the classes to remove from the element
   * @see #removeCssClasses(Parent, String...)
   */
  default void removeCssClasses(@NotNull String @NotNull ... classNames) {
    checkThis();
    removeCssClasses((Parent) this, classNames);
  }

  /**
   * Removes the given classes from the element.
   *
   * @param element    the element to remove the classes from
   * @param classNames the classes to remove
   */
  default void removeCssClasses(@NotNull Parent element, @NotNull String @NotNull ... classNames) {
    element.getStyleClass().removeAll(classNames);
  }

  /**
   * Shorthand for toggling the given class on the 'this' element.
   *
   * @param className the class to toggle
   * @return whether the class was added
   * @see #toggleCssClass(Parent, String)
   */
  default boolean toggleCssClass(@NotNull String className) {
    checkThis();
    return toggleCssClass((Parent) this, className);
  }

  /**
   * Toggles the given class on the element.
   *
   * @param element   the element to toggle the class on
   * @param className the class to toggle
   * @return whether the class was added
   */
  default boolean toggleCssClass(@NotNull Parent element, @NotNull String className) {
    if (hasCssClass(className)) {
      element.getStyleClass().remove(className);
      return false;
    } else {
      element.getStyleClass().add(className);
      return true;
    }
  }

  /**
   * Shorthand for checking if the 'this' element has the given class.
   *
   * @param className the class to check for
   * @return whether the element has the class
   * @see #hasCssClass(Parent, String)
   */
  default boolean hasCssClass(@NotNull String className) {
    checkThis();
    return hasCssClass((Parent) this, className);
  }

  /**
   * Checks if the element has the given class.
   *
   * @param element   the element to check
   * @param className the class to check for
   * @return whether the element has the class
   */
  default boolean hasCssClass(@NotNull Parent element, @NotNull String className) {
    return element.getStyleClass().contains(className);
  }
}
