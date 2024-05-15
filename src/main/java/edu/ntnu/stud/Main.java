package edu.ntnu.stud;

import edu.ntnu.stud.controller.AppController;
import edu.ntnu.stud.utils.FileLoader;
import edu.ntnu.stud.utils.GlobalData;
import edu.ntnu.stud.view.App;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * The main class for the application. Must be run with JavaFX.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class Main extends Application {

  /**
   * The main method for the application. Don't call this method directly.
   *
   * @param stage The stage to start the application in.
   */
  public void start(@NotNull Stage stage) {
    GlobalData.setIsRunningJavaFx();

    App application = new App();
    new AppController(application);

    Scene scene = new Scene(application, 500, 400);
    scene.getStylesheets().add(FileLoader.getStylesheet("root"));

    stage.setTitle("Chaos Game");
    stage.setScene(scene);
    stage.show();
  }
}