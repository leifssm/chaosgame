package edu.ntnu.stud.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

/**
 * A class representing the state of the application. Used to store the current state of the
 * application and to import and export the state to and from the resources/state.json file.
 *
 * @author Leif Mørstad
 * @version 1.2
 */
public class StateManager {
  private static final Logger LOGGER = Logger.getLogger(StateManager.class.getName());
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
  }

  /**
   * Imports the state from the state.json file. If an error occurs it returns a defaulted state.
   *
   * @return the state manager with the imported state
   */
  public static @NotNull StateManager importState() {
    StateManager state = new StateManager();
    JsonNode json = ResourceHandler.readFile("state.json");

    try {
      if (json == null) {
        throw new Exception();
      }
      ChaosGameDescription description = ChaosGameFileHandler.readChaosGame(
          json.get("currentFractalDescription")
      );
      state.currentFractalDescription.set(description);
      LOGGER.info("Successfully read fractal description from file.");
    } catch (Exception e) {
      LOGGER.severe("Could not read fractal description from file.");
    }

    return state;
  }

  public static void exportState(@NotNull StateManager state) {
    ResourceHandler.writeToFile("state.json", state);
    LOGGER.info("Successfully saved state to file.");
  }

  /**
   * Returns the width of the screen as a property.
   *
   * @return the width of the screen
   */
  @JsonIgnore
  public @NotNull SimpleIntegerProperty widthProperty() {
    return screenWidth;
  }

  /**
   * Returns the height of the screen as a property.
   *
   * @return the height of the screen
   */
  @JsonIgnore
  public @NotNull SimpleIntegerProperty heightProperty() {
    return screenHeight;
  }

  /**
   * Returns the flagger for the loading state.
   *
   * @return the flagger for the loading state
   */
  @JsonIgnore
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


  /**
   * Returns the current fractal description. Used for serialization.
   *
   * @return the current fractal description
   */
  @JsonProperty
  public @Nullable ChaosGameDescription getCurrentFractalDescription() {
    return currentFractalDescription.get();
  }
}
