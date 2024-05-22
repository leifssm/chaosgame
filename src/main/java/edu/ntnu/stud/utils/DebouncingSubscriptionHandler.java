package edu.ntnu.stud.utils;

import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

/**
 * A subscription handler that debounces the notification of subscribers.
 *
 * @param <ObservedT> the type of the observed value
 * @author Leif Mørstad
 * @version 1.0
 * @see SubscriptionHandler
 */
public class DebouncingSubscriptionHandler<ObservedT> extends SubscriptionHandler<ObservedT> {

  private final @NotNull Debouncer notifierDebouncer;

  /**
   * Creates a new instance with the given observed value and debounce duration.
   *
   * @param observed         the value to observe
   * @param debounceDuration the duration to debounce the notifications for
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