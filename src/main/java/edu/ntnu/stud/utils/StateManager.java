package edu.ntnu.stud.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.beans.property.SimpleIntegerProperty;
import org.jetbrains.annotations.NotNull;

/**
 * A class representing the state of the application.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class StateManager {

  private final @NotNull SimpleIntegerProperty screenWidth = new SimpleIntegerProperty(1);
  private final @NotNull SimpleIntegerProperty screenHeight = new SimpleIntegerProperty(1);
  private final @NotNull UsageFlagger isLoading = new UsageFlagger();

  /**
   * Creates a new instance of the state manager with default values.
   */
  public StateManager() {
    super();
  }

  /**
   * Imports the state from the state.json file. If an error occurs it returns a defaulted state.
   *
   * @return the state manager with the imported state
   */
  public static @NotNull StateManager importState() {
    StateManager state = new StateManager();
    JsonNode json = FileHandler.readFile("state.json");

    // add important properties here

    return state;
  }

  public static void exportState(@NotNull StateManager state) {
    FileHandler.writeToFile("state.json", state);
  }

  public static void main(String[] args) {
    StateManager stateManager = new StateManager();
    exportState(stateManager);
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
}
