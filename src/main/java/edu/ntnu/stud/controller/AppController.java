package edu.ntnu.stud.controller;

import edu.ntnu.stud.controller.controllers.Controller;
import edu.ntnu.stud.controller.controllers.SidebarController;
import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.IterativeChaosGame;
import edu.ntnu.stud.utils.Debouncer;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.utils.UsageFlagger.FlagSetter;
import edu.ntnu.stud.view.App;
import edu.ntnu.stud.view.components.FractalPane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * The controller for the {@link App} view.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class AppController extends Controller {

  private static final @NotNull StateManager state = StateManager.importState();
  private final @NotNull App application;
  private final @NotNull FlagSetter setIsRendering;
  private final @NotNull Debouncer notifyResize = new Debouncer(
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
    state.widthProperty().subscribe(n -> update());
    state.heightProperty().subscribe(n -> update());
    state.currentFractalDescription().subscribe(d -> updateFractalPane());

    FlagSetter isWaitingForResize = state.getIsLoading().createFlagSetter();
    notifyResize.getIsWaiting().subscribe(isWaitingForResize::setFlag);
    application.getSpinner().setHandler(state.getIsLoading());

    new SidebarController(application.getSidebarOverlay().getSidebar(), state);
  }

  private static int clampSize(@NotNull Number size) {
    return Math.max(2, size.intValue());
  }

  public void update() {
    notifyResize.run();
  }

  public @NotNull StateManager getState() {
    return state;
  }

  /**
   * Only run through debouncer.
   */
  private void updateFractalPane() {
    System.out.println(
        "Updating size to " + state.widthProperty().get() + "x" + state.heightProperty().get()
    );

    setIsRendering.setFlag(true);

    ChaosGameDescription fractalDescription = state.currentFractalDescription().get();

    if (fractalDescription == null) {
      application.replaceChaosPanel(null);
      setIsRendering.setFlag(false);
      return;
    }

    ChaosGame chaosGame = new IterativeChaosGame(
        state.widthProperty().get(),
        state.heightProperty().get(),
        fractalDescription
    );
    chaosGame.render();

    application.replaceChaosPanel(new FractalPane(chaosGame));
    state.currentFractal().set(chaosGame);
    setIsRendering.setFlag(false);
  }
}
