package edu.ntnu.stud.controller.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameDescriptionFactory;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import edu.ntnu.stud.utils.FileHandler;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.view.components.prompt.prompts.ErrorDialogFactory;
import edu.ntnu.stud.view.components.prompt.prompts.TransformationAmountDialog;
import edu.ntnu.stud.view.components.sidebaroverlay.Sidebar;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InvalidObjectException;

/**
 * Controller for the sidebar content. Handles logic connected to the {@link Sidebar} component.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class SidebarController {
  private final @NotNull Sidebar sidebar;
  private final @NotNull StateManager state;

  /**
   * Creates a new SidebarController for the given Sidebar instance.
   *
   * @param sidebar the sidebar to manage
   * @param state   the state manager to use
   */
  public SidebarController(@NotNull Sidebar sidebar, @NotNull StateManager state) {
    this.sidebar = sidebar;
    this.state = state;

    updateFractalList();
  }

  /**
   * Updates the fractal list in the sidebar.
   */
  private void updateFractalList() {
    sidebar.clear();
    sidebar.addFractalDisplay("Add Fractal +", this::startAddFractalFlow);
    sidebar.addFractalDisplay("Import Fractal", this::startImportFractalFlow);

    sidebar.addFractalDisplay("Factory Sierpinski", () -> {
      ChaosGameDescription game = ChaosGameDescriptionFactory.createSierpinski();
      runGame(game);
    });

    for (File fractal : ChaosGameFileHandler.getAllFractals()) {
      sidebar.addFractalDisplay(fractal.getName(), () -> runFile(fractal.getName()));
    }
  }

  /**
   * Sets the current fractal to the given game.
   *
   * @param game the game to set
   */
  private void runGame(@NotNull ChaosGameDescription game) {
    state.currentFractalDescription().set(game);
  }

  /**
   * Runs a chaos game from a file.
   *
   * @param fileName the name of the file to run, logs an error if the file could not be read
   */
  private void runFile(@NotNull String fileName) {
    try {
      ChaosGameDescription game = ChaosGameFileHandler.readFromFile(fileName);
      runGame(game);
    } catch (Exception e) {
      System.out.println("Could not read file: " + fileName);
      ErrorDialogFactory.show("Could not read file: " + fileName);
    }
  }

  /**
   * Starts the flow for adding a new fractal and updates the fractal list if the creation was
   * successful.
   */
  public void startAddFractalFlow() {
    boolean success = new TransformationAmountDialog().waitForResult();
    if (success) {
      updateFractalList();
    }
  }

  private void startImportFractalFlow() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Fractal File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Fractal Files", "*.json")
    );
    File selectedFile = fileChooser.showOpenDialog(sidebar.getScene().getWindow());
    if (selectedFile == null) {
      ErrorDialogFactory.create("Could not open file.").waitForResult();
      ErrorDialogFactory.show("Could not open file.");
      return;
    }
    JsonNode node = FileHandler.readFile(selectedFile);
    if (node == null) {
      ErrorDialogFactory.create("Invalid JSON format.").waitForResult();
      ErrorDialogFactory.show("Invalid JSON format.");
      return;
    }
    try {
      ChaosGameDescription game = ChaosGameFileHandler.readChaosGame(node);
      runGame(game);
    } catch (InvalidObjectException e) {
      ErrorDialogFactory.create("Invalid fractal file: " + e.getMessage()).waitForResult();
      ErrorDialogFactory.show("Invalid fractal file: " + e.getMessage());
    }
  }
}
