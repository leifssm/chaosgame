package edu.ntnu.stud.model;

import java.lang.reflect.Array;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

/**
 * A class for handling subscriptions to a value.
 *
 * @param <ObservedType> the type of the observed value
 * @author Leif Mørstad
 * @version 1.0
 */
public class SubscriptionHandler<ObservedType> {
  private int subscriptionLength = 0;
  private Consumer<ObservedType>[] subscriptions = createArray(0);
  private ObservedType observed;

  /**
   * Creates a new instance with the given observed value.
   *
   * @param observed the value to observe
   */
  public SubscriptionHandler(ObservedType observed) {
    this.observed = observed;
  }

  /**
   * Creates an empty array of the ObservedType type with the given length.
   *
   * @param length the length of the array
   * @return an array of the ObservedType type with the given length
   */
  private Consumer<ObservedType>[] createArray(int length) {
    // https://stackoverflow.com/questions/529085/how-can-i-create-a-generic-array-in-java

    @SuppressWarnings("unchecked")
    Consumer<ObservedType>[] array = (Consumer<ObservedType>[]) Array.newInstance(Consumer.class, length);
    return array;
  }

  /**
   * Ensures that the subscriptions array has the given length, or extends the array if not.
   *
   * @param newLength the length to ensure
   */
  private void ensureLength(int newLength) {
    if (subscriptions.length < newLength) {
      Consumer<ObservedType>[] newSubscriptions = createArray(newLength);
      if (subscriptions != null) {
        System.arraycopy(subscriptions, 0, newSubscriptions, 0, subscriptions.length);
      }
      subscriptions = newSubscriptions;
    }
  }

  /**
   * Subscribes to the observed value with the given consumer.
   *
   * @param subscription the consumer to subscribe
   */
  public void subscribe(@NotNull Consumer<ObservedType> subscription) {
    subscriptionLength++;
    ensureLength(subscriptionLength);
    subscriptions[subscriptionLength - 1] = subscription;
    subscription.accept(observed);
  }

  /**
   * Gives all subscriptions the current value.
   */
  public void notifySubscribers() {
    for (Consumer<ObservedType> subscription : subscriptions) {
      subscription.accept(observed);
    }
  }

  /**
   * Sets the observed value to the given value and notifies all subscribers.
   *
   * @param observed the new value to observe
   */
  public void set(ObservedType observed) {
    this.observed = observed;
    notifySubscribers();
  }

  /**
   * Returns the observed value.
   *
   * @return the observed value
   */
  public ObservedType get() {
    return observed;
  }

  /**
   * Disconnects all subscriptions.
   */
  public void disconnectAll() {
    subscriptionLength = 0;
    subscriptions = createArray(0);
  }

  /**
   * Disconnects the given subscription.
   *
   * @param subscription the subscription to disconnect
   * @return whether the subscription was disconnected
   */
  public boolean disconnect(Consumer<ObservedType> subscription) {
    for (int i = 0; i < subscriptionLength; i++) {
      if (subscriptions[i] == subscription) {
        System.arraycopy(subscriptions, i + 1, subscriptions, i, subscriptionLength - i - 1);
        subscriptionLength--;
        return true;
      }
    }
    return false;
  }
}
