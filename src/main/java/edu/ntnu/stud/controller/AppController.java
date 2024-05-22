package edu.ntnu.stud.controller;

import edu.ntnu.stud.controller.controllers.SidebarController;
import edu.ntnu.stud.controller.controllers.SidebarOverlayController;
import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.utils.Debouncer;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.utils.UsageFlagger.FlagSetter;
import edu.ntnu.stud.view.App;
import edu.ntnu.stud.view.components.FractalPane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * The controller for the {@link App} view. Handles logic connected to the application.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class AppController {

  private static final @NotNull StateManager state = StateManager.importState();
  private final @NotNull App application;
  private final @NotNull FlagSetter setIsRendering;
  private final @NotNull Debouncer updateFractalDebouncer = new Debouncer(
      this::updateFractalPane,
      Duration.millis(1000)
  );

  /**
   * Creates a new controller for an application and binds it.
   *
   * @param application the application to control
   */
  public AppController(@NotNull App application) {
    this.application = application;
    setIsRendering = state.getIsLoading().createFlagSetter();

    state.widthProperty().bind(application.widthProperty().map(AppController::clampSize));
    state.heightProperty().bind(application.heightProperty().map(AppController::clampSize));

    // It's necessary to not use subscribe(Runnable) as this does not subscribe to the value
    state.widthProperty().subscribe(n -> updateFractalDebouncer.run());
    state.heightProperty().subscribe(n -> updateFractalDebouncer.run());
    state.currentFractalDescription().subscribe(d -> updateFractalDebouncer.run());

    FlagSetter isWaitingForResize = state.getIsLoading().createFlagSetter();
    updateFractalDebouncer.getIsWaiting().subscribe(isWaitingForResize::setFlag);
    application.getSpinner().setHandler(state.getIsLoading());

    new SidebarOverlayController(application.getSidebarOverlay(), state);
    new SidebarController(application.getSidebarOverlay().getSidebar(), state);
  }

  /**
   * Makes sure the value isn't below 2
   *
   * @param size the size to clamp
   * @return the clamped size, being a minimum of 2
   */
  private static int clampSize(@NotNull Number size) {
    return Math.max(2, size.intValue());
  }

  /**
   * Gets the state manager.
   *
   * @return the state manager
   */
  public @NotNull StateManager getState() {
    return state;
  }

  /**
   * Rerenders the current fractal. Only run through the debouncer.
   */
  private void updateFractalPane() {
    setIsRendering.setFlag(true);
    ChaosGameDescription fractalDescription = state.currentFractalDescription().get();
    System.out.println(
        "Updating size to " + state.widthProperty().get() + "x" + state.heightProperty().get()
    );


    if (fractalDescription == null) {
      application.replaceChaosPanel(null);
      setIsRendering.setFlag(false);
      return;
    }

    ChaosGame chaosGame = new ChaosGame(
        state.widthProperty().get(),
        state.heightProperty().get(),
        fractalDescription
    );
    chaosGame.render();

    FractalPane chaosPane = new FractalPane(chaosGame);
    chaosGame.getCanvas().getSubscriptionHandler().subscribe(c -> chaosPane.render());

    application.replaceChaosPanel(chaosPane);

    state.currentFractal().set(chaosGame);
    setIsRendering.setFlag(false);
  }
}
