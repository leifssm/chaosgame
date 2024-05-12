package edu.ntnu.stud.utils;

import java.util.function.Consumer;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * A subscription handler that debounces the notification of subscribers.
 *
 * @param <ObservedType>
 * @see SubscriptionHandler
 * @version 1.0
 * @author Leif Mørstad
 */
public class DebouncingSubscriptionHandler<ObservedType> extends SubscriptionHandler<ObservedType> {
  private final Debouncer notifierDebouncer;

  /**
   * @see SubscriptionHandler#SubscriptionHandler(Object)
   */
  public DebouncingSubscriptionHandler(ObservedType observed, @NotNull Duration debounceDuration) {
    super(observed);
    notifierDebouncer = new Debouncer(super::notifySubscribers, debounceDuration);
  }


  @Override
  public void notifySubscribers() {
    notifierDebouncer.run();
  }
}