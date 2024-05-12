package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.Vector;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Enclosed.class)
public class IterativeChaosGameCanvasTest {
  private ChaosGameCanvas chaosGameCanvas;
  private final int width = 2;
  private final int height = 2;

  @BeforeEach
  void setUp() {
    chaosGameCanvas = new ChaosGameCanvas(width, height, new Vector(0, 0), new Vector(10, 10));
  }

  boolean isFilledWith(int value) {
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 2; x++) {
        if (chaosGameCanvas.getPixel(x, y) != value) {
          return false;
        }
      }
    }
    return true;
  }

  boolean isEmpty() {
    return isFilledWith(0);
  }

  boolean isFilled() {
    return isFilledWith(1);
  }

  @Nested
  class PositiveTests {
    @Test
    @DisplayName("clear() should set all pixels to 0")
    void clear() {
      chaosGameCanvas.touchPixel(0, 0);
      chaosGameCanvas.touchPixel(1, 1);
      chaosGameCanvas.clear();
      assertTrue(isEmpty());
    }

    @Test
    @DisplayName("get(x, y) get the pixel value at (x, y) from the bottom left corner")
    void getPixel() {
      int x = 0, y = height - 1;
      chaosGameCanvas.getCanvas()[y][x] = 1;

      assertEquals(
          1,
          chaosGameCanvas.getPixel(0, 0),
          "getPixel(x, y) should return the value at (x, y) from the bottom left corner"
      );
    }

    @Test
    @DisplayName("setPixel(x, y) set the pixel value at (x, y) from the bottom left corner")
    void setPixel() {
      int x = 0, y = height - 1;
      chaosGameCanvas.setPixel(0, 0, 1);

      assertEquals(
          1,
          chaosGameCanvas.getCanvas()[y][x],
          "setPixel(x, y) should set the value at (x, y) from the bottom left corner"
      );
    }

    @Test
    @DisplayName("Using setPixel(x, y) outside the canvas' bounds does nothing and does not throw")
    void setPixelDoesNothing() {
      chaosGameCanvas.setPixel(-3, 0, 1);
      chaosGameCanvas.setPixel(width + 3, 0, 1);
      chaosGameCanvas.setPixel(0, -3, 1);
      chaosGameCanvas.setPixel(0, height + 3, 1);

      assertTrue(isEmpty());
    }

    @Test
    @DisplayName("drawAtCoords(Vector) sets the pixel at the transformed coordinates")
    void drawAtCoords() {
      chaosGameCanvas.drawAtCoords(new Vector(0, 0));
      chaosGameCanvas.drawAtCoords(new Vector(0, 10));
      chaosGameCanvas.drawAtCoords(new Vector(10, 0));
      chaosGameCanvas.drawAtCoords(new Vector(10, 10));

      assertTrue(
          isFilled(),
          "drawAtCoords(Vector) should set the pixel at the transformed coordinates"
      );
    }

    @Test
    @DisplayName("asString() returns an ascii representation of the canvas")
    void asString() {
      chaosGameCanvas.setPixel(0, 0, 1);
      chaosGameCanvas.setPixel(1, 1, 1);

      assertEquals(
          " x\nx \n",
          chaosGameCanvas.asSimpleString(),
          "asString() should return an ascii representation of the canvas"
      );
    }
  }
}