package edu.ntnu.stud.view.components;

import edu.ntnu.stud.model.ChaosGame;
import edu.ntnu.stud.model.ChaosGameDescription;
import edu.ntnu.stud.model.ChaosGameFileHandler;
import edu.ntnu.stud.model.ReverseChaosGame;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChaosGamePanel extends ImageView {
  private @Nullable ChaosGame chaosGame;
  private final WritableImage image;
  private static final String DEFAULT_FILE_PATH = "julia.txt";

  public ChaosGamePanel(int width, int height) {
    super(new WritableImage(width, height));

    image = (WritableImage) getImage();
    replaceCurrentGame(DEFAULT_FILE_PATH);
  }

  public void replaceCurrentGame(@NotNull String filePath) {
    ChaosGameDescription file = ChaosGameFileHandler.readFromFile(filePath);
    disconnect();

    chaosGame = new ReverseChaosGame((int) image.getWidth(), (int) image.getHeight(), file);

    chaosGame.render();
    chaosGame.getCanvas().getSubscriptionHandler().subscribe(this::redraw);
  }

  private void redraw(int @NotNull [] @NotNull [] canvas) {
    for (int y = 0; y < canvas.length; y++) {
      for (int x = 0; x < canvas[y].length; x++) {
        drawPixel(x, y, canvas[y][x]);
      }
    }
  }

  private final Color[] colors = new Color[]{
    Color.rgb(0, 0, 0),
    Color.rgb(25, 7, 26),
    Color.rgb(9, 1, 47),
    Color.rgb(4, 4, 73),
    Color.rgb(0, 7, 100),
    Color.rgb(12, 44, 138),
    Color.rgb(24, 82, 177),
    Color.rgb(57, 125, 209),
    Color.rgb(134, 181, 229),
    Color.rgb(211, 236, 248),
    Color.rgb(241, 233, 191),
    Color.rgb(248, 201, 95),
    Color.rgb(255, 170, 0),
    Color.rgb(204, 128, 0),
    Color.rgb(153, 87, 0),
    Color.rgb(106, 52, 3)
  };
  private Color getColor(int color) {
    if (color < 0) {
      return colors[0];
    }
    if (color >= colors.length) {
      return colors[colors.length - 1];
    }
    return colors[color];
  }

  private void drawPixel(int x, int y, int color) {
    image.getPixelWriter().setColor(x, y, getColor(color));
  }

  public void disconnect() {
    if (chaosGame != null) {
      chaosGame.getCanvas().getSubscriptionHandler().disconnectAll();
    }
  }
}
