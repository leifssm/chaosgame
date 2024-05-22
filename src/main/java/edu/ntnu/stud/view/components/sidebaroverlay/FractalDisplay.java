package edu.ntnu.stud.view.components.sidebaroverlay;

import edu.ntnu.stud.utils.ResourceHandler;
import edu.ntnu.stud.view.components.ComponentUtils;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * A card representing a fractal the user can render
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

    // Potential for showing a preview of the fractal using the node.snapshot() method, but this
    // is beyond the current scope of the project.

    //Node fractalDisplay;
    //String fractalImageUrl = ResourceHandler.getImage(fractalName);
    //if (fractalImageUrl == null) {
    //  fractalDisplay = IconFactory.createIcon("layers-off");
    //} else {
    //  Image image = new Image(fractalImageUrl);
    //  fractalDisplay = new ImageView(image);
    //}

    Label label = new Label(ResourceHandler.removeFileExtension(fractalName));

    getChildren().add(label);
    setOnMouseClicked(event -> onClick.run());
  }
}
