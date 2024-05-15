package edu.ntnu.stud;

import edu.ntnu.stud.controller.AppController;
import edu.ntnu.stud.utils.FileLoader;
import edu.ntnu.stud.utils.GlobalData;
import edu.ntnu.stud.view.App;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
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