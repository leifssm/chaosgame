package edu.ntnu.stud;

import edu.ntnu.stud.controller.AppController;
import edu.ntnu.stud.utils.FileHandler;
import edu.ntnu.stud.utils.RuntimeInfo;
import edu.ntnu.stud.utils.StateManager;
import edu.ntnu.stud.view.App;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The main class for the application. Must be run with JavaFX.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class Main extends Application {

  /**
   * The main method for the application. Don't call this method directly.
   *
   * @param stage The stage to start the application in.
   */
  public void start(@NotNull Stage stage) {
    RuntimeInfo.setIsRunningJavaFx();

    App application = new App();
    var controller = new AppController(application);

    Scene scene = new Scene(application, 600, 500);
    scene.getStylesheets().add(FileHandler.getStylesheet("root"));

    stage.setTitle("Chaos Game");
    stage.setScene(scene);
    stage.getIcons().add(new Image(Objects.requireNonNull(FileHandler.getImage("icon.png"))));
    stage.show();
    stage.setOnHiding(event -> StateManager.exportState(controller.getState()));
  }
}