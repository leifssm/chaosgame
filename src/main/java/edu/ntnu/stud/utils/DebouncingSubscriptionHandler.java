package edu.ntnu.stud.utils;

import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * A subscription handler that debounces the notification of subscribers.
 *
 * @param <ObservedT> the type of the observed value
 * @see SubscriptionHandler
 * @version 1.0
 * @author Leif Mørstad
 */
public class DebouncingSubscriptionHandler<ObservedT> extends SubscriptionHandler<ObservedT> {
  private final Debouncer notifierDebouncer;

  /**
   * @see SubscriptionHandler#SubscriptionHandler(Object)
   */
  public DebouncingSubscriptionHandler(ObservedT observed, @NotNull Duration debounceDuration) {
    super(observed);
    notifierDebouncer = new Debouncer(super::notifySubscribers, debounceDuration);
  }


  @Override
  public void notifySubscribers() {
    notifierDebouncer.run();
  }
}