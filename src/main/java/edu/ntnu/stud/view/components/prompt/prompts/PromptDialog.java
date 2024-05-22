package edu.ntnu.stud.view.components.prompt.prompts;

import edu.ntnu.stud.view.components.prompt.PromptValidationError;
import edu.ntnu.stud.view.components.prompt.components.PromptField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PromptDialog extends Dialog<@NotNull Boolean> {
  private @NotNull PromptValidator extraValidator = () -> {
  };

  public PromptDialog(boolean cancelable, @NotNull String okText) {
    super();

    setTitle("Prompt");
    setResultConverter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE);

    if (cancelable) {
      createButton("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    }
    Node okButton = createButton(okText, ButtonBar.ButtonData.OK_DONE);

    okButton.addEventFilter(ActionEvent.ACTION, event -> {
      try {
        extraValidator.validate();
        for (var promptField : getDialogPane().lookupAll("PromptField")) {
          ((PromptField<?, ?>) promptField).validate();
        }
      } catch (PromptValidationError e) {
        event.consume();
        ErrorDialogFactory.show(e.getMessage());
      }
    });
  }

  public PromptDialog(@NotNull PromptDialogBuilder builder) {
    this(builder.cancelable, builder.okText);
    setTitle(builder.title);
    getDialogPane().setContent(builder.content);
    setExtraValidator(builder.extraValidator);
  }

  public PromptDialog() {
    this(new PromptDialogBuilder());
  }

  public void setContent(@Nullable Node content) {
    getDialogPane().setContent(content);
  }

  public void setExtraValidator(@NotNull PromptValidator validator) {
    this.extraValidator = validator;
  }

  private @NotNull Node createButton(
      @NotNull String content,
      @NotNull ButtonBar.ButtonData buttonData
  ) {
    ButtonType buttonType = new ButtonType(content, buttonData);
    getDialogPane().getButtonTypes().add(buttonType);
    Node button = getDialogPane().lookupButton(buttonType);

    String buttonStyle = buttonData == ButtonBar.ButtonData.CANCEL_CLOSE ? "cancel" : "ok";
    button.getStyleClass().add("button-" + buttonStyle);
    return button;
  }

  /**
   * Shows the dialog and returns a boolean after the dialog is closed, representing a success or an
   * abort.
   *
   * @return true if the dialog closed with valid input, false if the dialog was aborted
   */
  public boolean waitForResult() {
    return showAndWait().orElse(false);
  }

  public interface PromptValidator {
    void validate() throws PromptValidationError;
  }

  public static class PromptDialogBuilder {
    private @NotNull String title = "Dialog";
    private @Nullable Node content = null;
    private @NotNull String okText = "Ok";
    private boolean cancelable = false;
    private @NotNull PromptValidator extraValidator = () -> {
    };

    public @NotNull PromptDialogBuilder setTitle(@NotNull String title) {
      this.title = title;
      return this;
    }

    public @NotNull PromptDialogBuilder setContent(@NotNull Node content) {
      this.content = content;
      return this;
    }

    public @NotNull PromptDialogBuilder setOkText(@NotNull String okText) {
      this.okText = okText;
      return this;
    }

    public @NotNull PromptDialogBuilder setCancelable(boolean cancelable) {
      this.cancelable = cancelable;
      return this;
    }

    public @NotNull PromptDialogBuilder setExtraValidator(@NotNull PromptValidator validator) {
      this.extraValidator = validator;
      return this;
    }

    public @NotNull PromptDialog build() {
      return new PromptDialog(this);
    }
  }
}
