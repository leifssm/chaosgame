package edu.ntnu.stud.controller;

import edu.ntnu.stud.controller.menu.Menu;
import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class TextInterface {
  public static void main(String[] args) {
    new Menu("Chaos Game")
        .addOption("Run from file", TextInterface::runFile)
        .addOption("Exit", () -> System.exit(0))
        .start();
  }

  public static void runFile() {
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

  private static void runFile(@NotNull String file) {
    int iterations = InputParser.getInt(
        "How many iterations do you want?",
        i -> i > 0,
        "Please enter a number greater than or equal to 1"
    );
    System.out.println("Running file: " + file);

    ChaosGameDescription description = ChaosGameFileHandler.readFromFile(file);
    ChaosGame chaosGame = new ChaosGame(60, 20, description);
    chaosGame.iterate(iterations);

    System.out.println(chaosGame.getCanvas().asString());
  }
}
