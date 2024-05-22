package edu.ntnu.stud;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class for the Chaos Game application. Call by running <pre>mvn clean javafx:run</pre>
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ChaosGameApp extends Application {
  /**
   * The main method for the application.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    Main.launch();
  }

  @Override
  public void start(Stage stage) {
    new Main().start(stage);
  }
}
