package edu.ntnu.stud.controller.controllers;

import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.utils.UsageFlagger;
import edu.ntnu.stud.view.components.StandardButton;
import edu.ntnu.stud.view.components.prompt.prompts.GetIterationAmountDialog;
import edu.ntnu.stud.view.components.sidebaroverlay.ActionButton;
import edu.ntnu.stud.view.components.sidebaroverlay.SidebarOverlay;
import org.jetbrains.annotations.NotNull;

/**
 * @author Leif Mørstad
 * @version 0.1
 */
public class SidebarOverlayController {
  private final @NotNull SidebarOverlay sidebarOverlay;
  private final @NotNull StateManager state;
  private final UsageFlagger.FlagSetter isRendering;
  private boolean sidebarOpen = false;

  public SidebarOverlayController(
      @NotNull SidebarOverlay sidebarOverlay,
      @NotNull StateManager state
  ) {
    this.sidebarOverlay = sidebarOverlay;
    this.state = state;
    this.isRendering = state.getIsLoading().createFlagSetter();

    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "folder",
            "Toggle sidebar",
            this::toggleSidebar
        ).setType(StandardButton.Type.PRIMARY)
    );
    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "eraser",
            "Clear canvas",
            this::clearCanvas
        )
    );
    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "plus-one",
            "Iterate once",
            () -> iterate(1)
        )
    );
    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "skip-next",
            "Iterate 100 times",
            () -> iterate(100)
        )
    );
    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "skip-next",
            "Iterate 10k times",
            () -> iterate(10000)
        )
    );
    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "skip-forward",
            "Iterate 100k times",
            () -> iterate(100000)
        )
    );
    sidebarOverlay.getButtonGroup().addButton(
        new ActionButton(
            "numeric-9-plus-box",
            "Iterate custom amount",
            this::startInputFlow
        )
    );
  }

  private void toggleSidebar() {
    sidebarOpen = !sidebarOpen;
    sidebarOverlay.setState(sidebarOpen);
  }

  private void clearCanvas() {
    ChaosGame currentFractal = state.currentFractal().get();
    if (currentFractal == null) {
      return;
    }
    currentFractal.getCanvas().clear();
  }

  private void iterate(int amount) {
    ChaosGame currentFractal = state.currentFractal().get();
    if (currentFractal == null) {
      return;
    }

    // Does nothing sadly, as iterate blocks the thread, making the spinner not appear
    isRendering.setFlag(true);
    currentFractal.iterate(amount);
    isRendering.setFlag(false);
  }

  private void startInputFlow() {
    new GetIterationAmountDialog(this::iterate).waitForResult();
  }
}
