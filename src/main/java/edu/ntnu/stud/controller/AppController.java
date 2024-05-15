package edu.ntnu.stud.controller;

import edu.ntnu.stud.controller.controllers.Controller;
import edu.ntnu.stud.utils.Debouncer;
import edu.ntnu.stud.utils.UsageFlagger.FlagSetter;
import edu.ntnu.stud.view.App;
import edu.ntnu.stud.view.components.FractalPane;
import edu.ntnu.stud.view.utils.StateManager;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The controller for the {@link App} view.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class AppController implements Controller {

  private static final @NotNull StateManager state = new StateManager();
  private final @NotNull App application;
  private final @NotNull FlagSetter setIsRendering;
  private @Nullable FractalPane fractalPane;
  private final @NotNull Debouncer notifyResize = new Debouncer(
      this::updateSize,
      Duration.millis(1000)
  );

  /**
   * Creates a new controller for an application and binds it.
   *
   * @param application the application to control
   */
  public AppController(@NotNull App application) {
    this.application = application;
    state.widthProperty().bind(application.widthProperty().map(AppController::clampSize));
    state.heightProperty().bind(application.heightProperty().map(AppController::clampSize));
    state.widthProperty().subscribe(this::update);
    state.heightProperty().subscribe(this::update);

    // Necessary to keep the subscriptions "live", I don't know why
    state.widthProperty().subscribe(ignored -> {
    });

    FlagSetter isWaitingForResize = state.getIsLoading().createFlagSetter();

    notifyResize.getIsWaiting().subscribe(isWaitingForResize::setFlag);

    application.getSpinner().setHandler(state.getIsLoading());
    setIsRendering = state.getIsLoading().createFlagSetter();
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
  private void updateSize() {
    System.out.println(
        "Updating size to " + state.widthProperty().get() + "x" + state.heightProperty().get()
    );

    setIsRendering.setFlag(true);
    FractalPane oldPanel = fractalPane;
    if (oldPanel != null) {
      oldPanel.destroy();
    }
    fractalPane = new FractalPane(state.widthProperty().get(), state.heightProperty().get());
    application.replaceChaosPanel(oldPanel, fractalPane);
    setIsRendering.setFlag(false);
  }
}
