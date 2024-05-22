package edu.ntnu.stud.view.components;

import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * A standard button class with a default style.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class StandardButton extends Button implements ComponentUtils {
  /**
   * Creates a new instance with the given text and action.
   *
   * @param text   the text to display on the button
   * @param action the action to run when the button is clicked
   */
  public StandardButton(@NotNull String text, @NotNull Runnable action) {
    super(text);
    setOnAction(event -> action.run());
    useStylesheet("components/standard-button");
    setType(Type.DEFAULT);
  }

  /**
   * Sets the type of the button and removes the old ones.
   *
   * @param type the new type of the button
   */
  public StandardButton setType(@NotNull Type type) {
    String[] classes = Arrays.stream(Type.values())
        .map(enumm -> enumm.cssClass)
        .toArray(String[]::new);

    removeCssClasses(classes);
    addCssClasses(type.cssClass);
    return this;
  }

  /**
   * The type of the button.
   */
  public enum Type {
    DEFAULT("default"),
    PRIMARY("primary");

    private final String cssClass;

    Type(String cssClass) {
      this.cssClass = cssClass;
    }
  }
}
