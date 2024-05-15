package edu.ntnu.stud.cli;

import java.io.File;
import edu.ntnu.stud.cli.menu.Menu;
import edu.ntnu.stud.model.IterativeChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import org.jetbrains.annotations.NotNull;

/**
 * A simple CLI for the chaos game.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class TextInterface {
  /**
   * Starts the CLI.
   *
   * @param args the command line arguments, unused
   */
  public static void main(String[] args) {
    new Menu("Chaos Game")
        .addOption("Run from file", TextInterface::runFilePrompt)
        .addOption("Exit", () -> System.exit(0))
        .start();
  }

  /**
   * Prompts the user to choose a file to run. Gives the option to pick any files from the
   * "src/main/resources/fractals" folder.
   */
  public static void runFilePrompt() {
    File fractalFolder = new File("src/main/resources/fractals/");
    try {
      fractalFolder.createNewFile();
    } catch (Exception e) {
      System.out.println("Could not create fractals folder");
      return;
    }

    File[] files = fractalFolder.listFiles((dir, name) -> name.endsWith(".txt"));
    if (files == null) {
      System.out.println("No files found");
      return;
    }

    Menu fileMenu = new Menu("Choose file");

    for (File file : files) {
      fileMenu.addOption(file.getName(), () -> runFile(file.getName()));
    }

    fileMenu.runOnce();
  }

  /**
   * Asks how many iterations the user wants, before it runs a chaos game from a given file and
   * displays the fractal.
   *
   * @param fileName the name of the file to run, expects the file to exist
   */
  private static void runFile(@NotNull String fileName) {
    int iterations = InputParser.getInt(
        "How many iterations do you want?",
        i -> i > 0,
        "Please enter a number greater than or equal to 1"
    );
    System.out.println("Running file: " + fileName);

    ChaosGameDescription description = ChaosGameFileHandler.readFromFile(fileName);
    IterativeChaosGame chaosGame = new IterativeChaosGame(60, 20, description);
    chaosGame.iterate(iterations);

    System.out.println(chaosGame.getCanvas().asSimpleString());
  }
}
