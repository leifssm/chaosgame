package edu.ntnu.stud.controller.controllers;

import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.view.components.StandardButton;
import edu.ntnu.stud.view.components.sidebaroverlay.ActionButton;
import edu.ntnu.stud.view.components.sidebaroverlay.SidebarOverlay;
import org.jetbrains.annotations.NotNull;

/**
 * @author Leif Mørstad
 * @version 0.1
 */
public class SidebarOverlayController extends Controller {
  private final @NotNull SidebarOverlay sidebarOverlay;
  private final @NotNull StateManager state;
  private boolean sidebarOpen = false;

  public SidebarOverlayController(
      @NotNull SidebarOverlay sidebarOverlay,
      @NotNull StateManager state
  ) {
    this.sidebarOverlay = sidebarOverlay;
    this.state = state;

    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "folder",
            "Open sidebar",
            this::toggleSidebar
        ).setType(StandardButton.Type.PRIMARY)
    );
    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "plus",
            "Add fractal",
            () -> System.out.println("Add fractal")
        )
    );
  }

  private void toggleSidebar() {
    sidebarOpen = !sidebarOpen;
    sidebarOverlay.setState(sidebarOpen);
  }

  public void update() {
  }
}
