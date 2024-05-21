package edu.ntnu.stud;

import javafx.application.Application;
import javafx.stage.Stage;

public class ChaosGameApp extends Application {
  public static void main(String[] args) {
    Main.launch();
  }

  @Override
  public void start(Stage stage) {
    new Main().start(stage);
  }
}
