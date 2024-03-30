package edu.ntnu.stud;

import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;

public class Main {
  public static void main(String[] args) {
    ChaosGameDescription description = ChaosGameFileHandler.readFromFile("sierpinski.txt");
    ChaosGame chaosGame = new ChaosGame(60, 20, description);
    chaosGame.iterate(500);
    System.out.println(chaosGame.getCanvas().asString());
  }
}