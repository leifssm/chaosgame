package edu.ntnu.stud.view.components;

import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class StandardButton extends Button implements ComponentUtils {
  public StandardButton(String text, Runnable action) {
    super(text);
    setOnAction(event -> action.run());
    useStylesheet("components/standard-button");
    setType(Type.DEFAULT);
  }

  /**
   * Sets the type of the button.
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

  public enum Type {
    DEFAULT("default"),
    PRIMARY("primary");

    private final String cssClass;

    Type(String cssClass) {
      this.cssClass = cssClass;
    }
  }
}
