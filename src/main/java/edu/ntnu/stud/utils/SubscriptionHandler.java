package edu.ntnu.stud.utils;

import java.lang.reflect.Array;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

/**
 * A class for handling subscriptions to a value. Used instead of SimpleObjectProperty (or similar)
 * because of the need for more control over the subscription notifying.
 *
 * @param <ObservedT> the type of the observed value
 * @author Leif Mørstad
 * @version 1.2
 */
public class SubscriptionHandler<ObservedT> {
  private int subscriptionLength = 0;
  private Consumer<ObservedT>[] subscriptions = createArray(0);
  private ObservedT observed;

  /**
   * Creates a new instance with the given observed value.
   *
   * @param observedValue the value to observe
   */
  public SubscriptionHandler(ObservedT observedValue) {
    this.observed = observedValue;
  }

  /**
   * Creates an empty array of the ObservedT type with the given length.
   *
   * @param length the length of the array
   * @return an array of the ObservedT type with the given length
   */
  private Consumer<ObservedT>[] createArray(int length) {
    // https://stackoverflow.com/questions/529085/how-can-i-create-a-generic-array-in-java

    @SuppressWarnings("unchecked")
    Consumer<ObservedT>[] array = (Consumer<ObservedT>[]) Array.newInstance(Consumer.class, length);
    return array;
  }

  /**
   * Ensures that the subscriptions array has the given length, or extends the array if not.
   *
   * @param newLength the length to ensure
   */
  private void ensureLength(int newLength) {
    if (subscriptions.length >= newLength) {
      return;
    }
    Consumer<ObservedT>[] newSubscriptions = createArray(newLength);
    System.arraycopy(subscriptions, 0, newSubscriptions, 0, subscriptions.length);
    subscriptions = newSubscriptions;
  }

  /**
   * Subscribes to the observed value with the given consumer.
   *
   * @param subscription the consumer to subscribe
   */
  public @NotNull Runnable subscribe(@NotNull Consumer<ObservedT> subscription) {
    subscriptionLength++;
    ensureLength(subscriptionLength);
    subscriptions[subscriptionLength - 1] = subscription;
    subscription.accept(observed);
    return () -> unsubscribe(subscription);
  }

  /**
   * Gives all subscriptions the current value.
   */
  public void notifySubscribers() {
    for (Consumer<ObservedT> subscription : subscriptions) {
      subscription.accept(observed);
    }
  }

  /**
   * Checks if the given observed value is different from the current observed value.
   *
   * @param observed the value to compare with the current observed value
   * @return whether the given value is different from the current observed value
   */
  private boolean isDifferent(ObservedT observed) {
    if (this.observed == null) {
      return observed != null;
    }
    if (observed == null) {
      return true;
    }
    return !this.observed.equals(observed);
  }

  /**
   * Sets the observed value to the given value and notifies all subscribers if.
   *
   * @param observed the new value to observe
   */
  public void set(ObservedT observed) {
    boolean shouldNotify = isDifferent(observed);
    this.observed = observed;
    if (shouldNotify) {
      notifySubscribers();
    }
  }

  /**
   * Sets the observed value to the given value and notifies all subscribers, whether the
   * value is different or not.
   *
   * @param observed the new value to observe
   */
  public void setAndForceNotify(ObservedT observed) {
    this.observed = observed;
    notifySubscribers();
  }

  /**
   * Sets the observed value to the given value without notifying subscribers.
   *
   * @param observed the new value to observe
   */
  public void setAndDontNotify(ObservedT observed) {
    this.observed = observed;
  }

  /**
   * Returns the observed value.
   *
   * @return the observed value
   */
  public ObservedT get() {
    return observed;
  }

  /**
   * Disconnects all subscriptions.
   */
  public void unsubscribeAll() {
    subscriptionLength = 0;
    subscriptions = createArray(0);
  }

  /**
   * Disconnects the given subscription.
   *
   * @param subscription the subscription to disconnect
   * @return whether the subscription was disconnected
   */
  public boolean unsubscribe(Consumer<ObservedT> subscription) {
    for (int i = 0; i < subscriptionLength; i++) {
      if (subscriptions[i].equals(subscription)) {
        System.arraycopy(subscriptions, i + 1, subscriptions, i, subscriptionLength - i - 1);
        subscriptionLength--;

        Consumer<ObservedT>[] a = createArray(subscriptionLength);
        System.arraycopy(subscriptions, 0, a, 0, subscriptionLength);
        subscriptions = a;

        return true;
      }
    }
    return false;
  }
}
