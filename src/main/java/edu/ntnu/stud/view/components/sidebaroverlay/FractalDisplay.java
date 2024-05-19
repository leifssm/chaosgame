package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.utils.FileLoader;
import edu.ntnu.stud.utils.StringUtils;
import edu.ntnu.stud.view.components.ComponentUtils;
import edu.ntnu.stud.view.utils.IconUtils;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a card displaying a fractal the user can render
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class FractalDisplay extends VBox implements ComponentUtils {

  /**
   * Creates a new card displaying a fractal the user can render
   *
   * @param fractalName the filename to use
   * @param onClick     the action to run when the card is clicked
   */
  public FractalDisplay(@NotNull String fractalName, @NotNull Runnable onClick) {
    super();
    addCssClasses("fractal-display");
    Node fractalDisplay;

    String fractalImageUrl = FileLoader.getImage(fractalName);
    if (fractalImageUrl == null) {
      fractalDisplay = IconUtils.createIcon("layers-off");
    } else {
      Image image = new Image(fractalImageUrl);
      fractalDisplay = new ImageView(image);
    }

    String cleanFractalName = FileLoader
        .removeFileExtension(fractalName)
        .replaceAll("[-_ ]+", " ");
    cleanFractalName = StringUtils.capitalizeWords(cleanFractalName);

    Label label = new Label(cleanFractalName);

    getChildren().addAll(fractalDisplay, label);
    setOnMouseClicked(event -> onClick.run());
  }
}
