package edu.ntnu.stud.view;

import edu.ntnu.stud.utils.FileLoader;
import edu.ntnu.stud.view.components.ComponentUtils;
import edu.ntnu.stud.view.views.Home;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class App extends Application implements ComponentUtils {
  public void start(@NotNull Stage stage) {
    Scene scene = new Scene(new Home(), 400, 300);
    scene.getStylesheets().add(FileLoader.getStylesheet("root"));

    stage.setTitle("Chaos Game");
    stage.setScene(scene);
    stage.show();
  }
}
