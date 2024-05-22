package edu.ntnu.stud.controller.controllers;

import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.utils.UsageFlagger;
import edu.ntnu.stud.view.components.StandardButton;
import edu.ntnu.stud.view.components.prompt.prompts.ErrorDialogFactory;
import edu.ntnu.stud.view.components.prompt.prompts.GetIterationAmountDialog;
import edu.ntnu.stud.view.components.prompt.prompts.ModifyFractalDialogFactory;
import edu.ntnu.stud.view.components.prompt.prompts.TransformationInputDialog;
import edu.ntnu.stud.view.components.sidebaroverlay.ActionButton;
import edu.ntnu.stud.view.components.sidebaroverlay.SidebarOverlay;
import org.jetbrains.annotations.NotNull;

/**
 * Controller for the sidebar overlay. Handles logic connected to the {@link SidebarOverlay}
 * component.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class SidebarOverlayController {
  private final @NotNull SidebarOverlay sidebarOverlay;
  private final @NotNull StateManager state;
  private final UsageFlagger.FlagSetter isRendering;
  private boolean sidebarOpen = false;

  /**
   * Creates a new controller for the given SidebarOverlay instance.
   *
   * @param sidebarOverlay the sidebar overlay to manage
   * @param state          the state manager to use
   */
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
            "pencil",
            "Edit fractal",
            this::editFractalFlow
        )
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

  /**
   * Toggles the state of the sidebar overlay.
   */
  private void toggleSidebar() {
    sidebarOpen = !sidebarOpen;
    sidebarOverlay.setState(sidebarOpen);
  }

  /**
   * Clears the canvas of the current fractal.
   */
  private void clearCanvas() {
    ChaosGame currentFractal = state.currentFractal().get();
    if (currentFractal == null) {
      return;
    }
    currentFractal.getCanvas().clear();
  }

  /**
   * Iterates the current fractal the given amount of times.
   *
   * @param amount the amount of iterations to perform
   */
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

  /**
   * Starts the flow for inputting a custom amount of iterations, and then iterates the amount of
   * times.
   */
  private void startInputFlow() {
    new GetIterationAmountDialog(this::iterate).waitForResult();
  }

  private void editFractalFlow() {
    ChaosGameDescription game = state.currentFractalDescription().get();
    if (game == null) {
      ErrorDialogFactory.create("No fractal selected.").waitForResult();
      return;
    }
    TransformationInputDialog dialog = ModifyFractalDialogFactory.create(true, game);
    dialog.waitForResult((n, newGame) -> state.currentFractalDescription().set(newGame));
  }
}
