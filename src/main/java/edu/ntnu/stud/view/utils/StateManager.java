package edu.ntnu.stud.view.utils;

import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.utils.UsageFlagger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class representing the state of the application.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class StateManager {

  private final @NotNull SimpleIntegerProperty screenWidth = new SimpleIntegerProperty(1);
  private final @NotNull SimpleIntegerProperty screenHeight = new SimpleIntegerProperty(1);
  private final @NotNull UsageFlagger isLoading = new UsageFlagger();
  private final @NotNull SimpleObjectProperty<@Nullable ChaosGame> currentFractal =
      new SimpleObjectProperty<>(null);
  private final @NotNull SimpleObjectProperty<@Nullable ChaosGameDescription>
      currentFractalDescription = new SimpleObjectProperty<>();

  /**
   * Creates a new instance of the state manager with default values.
   */
  public StateManager() {
    super();
  }

  public static void importState() {

  }

  public static void exportState() {

  }

  /**
   * Returns the width of the screen as a property.
   *
   * @return the width of the screen
   */
  public @NotNull SimpleIntegerProperty widthProperty() {
    return screenWidth;
  }

  /**
   * Returns the height of the screen as a property.
   *
   * @return the height of the screen
   */
  public @NotNull SimpleIntegerProperty heightProperty() {
    return screenHeight;
  }

  /**
   * Returns the flagger for the loading state.
   *
   * @return the flagger for the loading state
   */
  public @NotNull UsageFlagger getIsLoading() {
    return isLoading;
  }

  /**
   * Returns current rendered fractal as a property.
   *
   * @return the current rendered fractal
   */
  public @NotNull SimpleObjectProperty<@Nullable ChaosGame> currentFractal() {
    return currentFractal;
  }

  /**
   * Returns current fractal description as a property.
   *
   * @return the current fractal description
   */
  public @NotNull SimpleObjectProperty<@Nullable ChaosGameDescription> currentFractalDescription() {
    return currentFractalDescription;
  }
}
