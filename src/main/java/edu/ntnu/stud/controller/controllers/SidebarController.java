package edu.ntnu.stud.controller.controllers;

import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.view.components.sidebaroverlay.Sidebar;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Controller for the sidebar content.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class SidebarController extends Controller {
  private final @NotNull Sidebar sidebar;
  private final @NotNull StateManager state;

  public SidebarController(@NotNull Sidebar sidebar, @NotNull StateManager state) {
    this.sidebar = sidebar;
    this.state = state;

    update();
  }

  public void update() {
    sidebar.clear();
    for (File fractal : ChaosGameFileHandler.getAllFractals()) {
      sidebar.addFractalDisplay(fractal.getName(), () -> runFile(fractal.getName()));
    }
  }

  private void runFile(@NotNull String fileName) {
    try {
      ChaosGameDescription game = ChaosGameFileHandler.readFromFile(fileName);
      state.currentFractalDescription().set(game);
    } catch (Exception e) {
      System.out.println("Could not read file: " + fileName);
    }
  }
}
