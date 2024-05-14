package edu.ntnu.stud.view.utils;

import javafx.animation.Animation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class that adds helper methods for handling animations.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class AnimationHandler {
  private @Nullable Animation currentAnimation = null;

  /**
   * Creates a new instance with the given animation, and plays it.
   *
   * @param animation the animation to play
   */
  public AnimationHandler(@NotNull Animation animation) {
    this.currentAnimation = animation;
    animation.play();
  }

  /**
   * Creates a new instance and wait until given a task.
   */
  public AnimationHandler() {}

  /**
   * Returns whether the handler is currently animating.
   *
   * @return whether the handler is currently animating
   */
  public boolean isAnimating() {
    return currentAnimation != null;
  }

  /**
   * Replaces the current animation with the given one. Stops the current animation if it exists and
   * plays the new one.
   *
   * @param animation the new animation to play
   */
  public void replaceAnimation(@NotNull Animation animation) {
    stopCurrentAnimation();
    animation.onFinishedProperty().subscribe(() -> stopCurrentAnimation(animation));
    this.currentAnimation = animation;
    animation.play();
  }

  /**
   * Stops the current animation if it exists.
   */
  public void stopCurrentAnimation() {
    if (isAnimating()) {
      assert this.currentAnimation != null;
      this.currentAnimation.stop();
      this.currentAnimation = null;
    }
  }

  /**
   * Stops the current animation if it is the given one.
   *
   * @param animation the animation to stop
   */
  public void stopCurrentAnimation(@NotNull Animation animation) {
    if (this.currentAnimation == animation) {
      stopCurrentAnimation();
    }
  }

  /**
   * Pauses the current animation if it exists.
   */
  public void pause() {
    if (isAnimating()) {
      assert this.currentAnimation != null;
      this.currentAnimation.pause();
    }
  }

  /**
   * Plays the current animation if it exists.
   */
  public void play() {
    if (isAnimating()) {
      assert this.currentAnimation != null;
      this.currentAnimation.play();
    }
  }
}
