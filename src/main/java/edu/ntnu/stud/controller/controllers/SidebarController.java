package edu.ntnu.stud.controller.controllers;

import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.view.components.prompt.prompts.TransformationAmountDialog;
import edu.ntnu.stud.view.components.sidebaroverlay.Sidebar;
import org.jetbrains.annotations.NotNull;

import java.io.File;

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
    for (File fractal : ChaosGameFileHandler.getAllFractals()) {
      sidebar.addFractalDisplay(fractal.getName(), () -> runFile(fractal.getName()));
    }
  }

  /**
   * Runs a chaos game from a file.
   *
   * @param fileName the name of the file to run, logs an error if the file could not be read
   */
  private void runFile(@NotNull String fileName) {
    try {
      ChaosGameDescription game = ChaosGameFileHandler.readFromFile(fileName);
      state.currentFractalDescription().set(game);
    } catch (Exception e) {
      System.out.println("Could not read file: " + fileName);
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
}
