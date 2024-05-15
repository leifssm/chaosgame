package edu.ntnu.stud.view.components;

import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import edu.ntnu.stud.model.ReverseChaosGame;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javax.security.auth.Destroyable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FractalPane extends ImageView implements Destroyable {
  private @Nullable ChaosGame chaosGame;
  private final WritableImage image;
  private static final String DEFAULT_FILE_PATH = "julia.txt";

  public FractalPane(int width, int height) {
    super(new WritableImage(width, height));

    image = (WritableImage) getImage();
    replaceCurrentGame(DEFAULT_FILE_PATH);
  }

  public void replaceCurrentGame(@NotNull String filePath) {
    ChaosGameDescription file = ChaosGameFileHandler.readFromFile(filePath);
    destroy();

    chaosGame = new ReverseChaosGame((int) image.getWidth(), (int) image.getHeight(), file);

    chaosGame.render();
    chaosGame.getCanvas().getSubscriptionHandler().subscribe(this::redraw);
  }

  private void redraw(int @NotNull [] @NotNull [] canvas) {
    Platform.runLater(() -> {
      // https://stackoverflow.com/questions/8935367/convert-a-2d-array-into-a-1d-array
      int[] flattened = Stream.of(canvas)
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
  private int mapColor(int color) {
    if (color < 0) {
      return colors[0];
    }
    if (color >= colors.length) {
      return colors[colors.length - 1];
    }
    return colors[color];
  }

  private void drawPixel(int x, int y, int color) {
//    image.getPixelWriter().setColor(x, y, getColor(color));
  }

  @Override
  public void destroy() {
    if (chaosGame != null) {
      chaosGame.getCanvas().getSubscriptionHandler().unsubscribeAll();
    }
  }
}
