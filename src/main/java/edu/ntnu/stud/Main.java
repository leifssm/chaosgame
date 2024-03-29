package edu.ntnu.stud;

import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.math.AffineTransformationGroup;
import edu.ntnu.stud.model.math.Vector;

public class Main {
  public static void main(String[] args) {
    ChaosGameDescription description = new ChaosGameDescription(
        new Vector(0, 0),
        new Vector(3, 1),
        AffineTransformationGroup.sierpinski
    );
    ChaosGame chaosGame = new ChaosGame(60, 20, description);
    chaosGame.iterate(10000);
    System.out.println(chaosGame.getCanvas().asString());
  }
}