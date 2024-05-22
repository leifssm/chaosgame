package edu.ntnu.stud.view.components.prompt.components;

import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class representing one field in a prompt.
 *
 * @param <Component>   the type of the input component
 * @param <ReturnValue> the type of the value returned by the field
 */
public class PromptField<Component extends Node, ReturnValue> extends BorderPane {
  private final @NotNull Component input;
  private final @NotNull ResultConverter<Component, ReturnValue> resultConverter;
  private final @NotNull PromptErrorStack errorStack;
  private @Nullable ReturnValue cachedValue = null;

  /**
   * Creates a field, based on a name, a component, and a converter.
   *
   * @param fieldName       the name of the field
   * @param component       the base component
   * @param resultConverter a function taking the comonent and returns the wanted value
   */
  public PromptField(
      @NotNull String fieldName,
      @NotNull Component component,
      @NotNull ResultConverter<Component, ReturnValue> resultConverter
  ) {
    super();
    setLeft(new Label(fieldName + ": "));
    setCenter(component);
    this.resultConverter = resultConverter;
    input = component;
    errorStack = new PromptErrorStack(fieldName);
  }

  // has to be textfiels
  public PromptField(
      @NotNull String fieldName,
      @NotNull Component component,
      @Nullable String initialValue,
      @NotNull ResultConverter<Component, ReturnValue> resultConverter
  ) {
    this(fieldName, component, resultConverter);
    if (initialValue != null) {
      ((TextField) component).setText(initialValue);
    }
  }

  /**
   * Returns the error stack of the field to use if the data is invalid for easier logging.
   *
   * @return the error stack
   */
  public @NotNull PromptErrorStack getErrorStack() {
    return errorStack;
  }

  /**
   * Returns the converted value of the field using the resultConverter function.
   *
   * @return the converted value
   * @throws PromptValidationError if the value is invalid
   */
  public ReturnValue getValue() throws PromptValidationError {
    ReturnValue value = resultConverter.convert(input, errorStack.compile());
    cachedValue = value;
    return value;
  }

  /**
   * Returns the cached value of the field. The value will always be set after validation. Throws
   * an exception if the value has not been set yet, and it's accessed before validation.
   *
   * @return the cached value
   */
  public ReturnValue getCachedValue() {
    if (cachedValue == null) {
      throw new IllegalStateException("Value has not been set yet.");
    }
    return cachedValue;
  }

  /**
   * Throws if the field is invalid.
   *
   * @throws PromptValidationError if the field is invalid
   */
  public void validate() throws PromptValidationError {
    getValue();
  }

  /**
   * A function for converting the component to the wanted value.
   *
   * @param <Component>   the type of the input component
   * @param <ReturnValue> the type of the value returned by the field
   */
  public interface ResultConverter<Component, ReturnValue> {
    ReturnValue convert(
        @NotNull Component component,
        @NotNull String errorStack
    ) throws PromptValidationError;
  }
}
