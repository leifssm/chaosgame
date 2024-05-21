package edu.ntnu.stud.view.components;

import edu.ntnu.stud.model.ChaosGame;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A pane that displays a fractal image rendered from a {@link ChaosGame}.
 *
 * @author Leif Mørstad
 * @version 2.0
 */
public class FractalPane extends ImageView {

  private final WritableImage image;
  private final int[] colors = new int[]{
      0xFF000000,
      0xFF19071A,
      0xFF09012F,
      0xFF040449,
      0xFF000764,
      0xFF0C2C8A,
      0xFF1852B1,
      0xFF397DD1,
      0xFF86B5E5,
      0xFFD3ECF8,
      0xFFF1E9BF,
      0xFFF8C477,
      0xFFFFAA00,
      0xFFCC8000,
      0xFF995700,
      0xFF6A3403
  };
  private final @NotNull ChaosGame chaosGame;

  /**
   * Creates a new instance with the given chaos game.
   *
   * @param chaosGame the chaos game to render
   */
  public FractalPane(ChaosGame chaosGame) {
    super(
        new WritableImage(
            chaosGame.getCanvas().getWidth(),
            chaosGame.getCanvas().getHeight()
        )
    );
    this.chaosGame = chaosGame;

    image = (WritableImage) getImage();
    render();
  }

  public void changeZoom() {
    //chaosGame.getCanvas().setView();
  }

  public void render() {
    Platform.runLater(() -> {
      // https://stackoverflow.com/questions/8935367/convert-a-2d-array-into-a-1d-array
      int[] flattened = Stream.of(chaosGame.getCanvas().getCanvas())
          .flatMapToInt(IntStream::of)
          .map(this::mapColor)
          .toArray();

      // not stack overflow
      image.getPixelWriter().setPixels(
          0, // x destination
          0,   // y destination
          (int) image.getWidth(), // destination width
          (int) image.getHeight(), // destination height
          PixelFormat.getIntArgbInstance(), // data type
          flattened, // data
          0, // x offset
          (int) image.getWidth() // y offset
      );
    });
  }

  private int mapColor(int color) {
    if (color < 0) {
      return colors[0];
    }
    if (color >= colors.length) {
      return colors[colors.length - 1];
    }
    return colors[color];
  }
}
