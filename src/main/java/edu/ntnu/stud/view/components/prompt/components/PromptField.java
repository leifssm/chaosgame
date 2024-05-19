package edu.ntnu.stud.view.components.prompt.components;

import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PromptField<Component extends Node, ReturnValue> extends BorderPane {
  private final @NotNull Component input;
  private final @NotNull ResultConverter<Component, ReturnValue> resultConverter;
  private final @NotNull PromptErrorStack errorStack;
  private @Nullable ReturnValue cachedValue = null;

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

  public @NotNull PromptErrorStack getErrorStack() {
    return errorStack;
  }

  public ReturnValue getValue() throws PromptValidationError {
    ReturnValue value = resultConverter.convert(input, errorStack.compile());
    cachedValue = value;
    return value;
  }

  public ReturnValue getCachedValue() {
    if (cachedValue == null) {
      throw new IllegalStateException("Value has not been set yet.");
    }
    return cachedValue;
  }

  public void validate() throws PromptValidationError {
    getValue();
  }

  public interface ResultConverter<Component, ReturnValue> {
    ReturnValue convert(
        Component component,
        @NotNull String errorStack
    ) throws PromptValidationError;
  }
}
