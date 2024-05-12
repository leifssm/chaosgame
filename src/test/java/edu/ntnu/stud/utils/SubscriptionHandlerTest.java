package edu.ntnu.stud.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class SubscriptionHandlerTest {
  @Nested
  class PositiveTests {
    @Test
    @DisplayName("Subscribing runs the subscriber once")
    void subscribingGivesValueAtOnce() {
      AtomicInteger updates = new AtomicInteger(0);
      SubscriptionHandler<Integer> value = new SubscriptionHandler<>(0);

      value.subscribe(i -> updates.incrementAndGet());

      assertEquals(
          1,
          updates.get(),
          "The subscriber should have been notified of the value when first subscribing."
      );
    }

    @Test
    @DisplayName("Changing the value notifies subscribers")
    void subscriptionsUpdateListeners() {
      AtomicBoolean didUpdate = new AtomicBoolean(false);
      SubscriptionHandler<Integer> value = new SubscriptionHandler<>(0);
      int newValue = 1;

      value.subscribe(subscribedValue ->
          {
            System.out.println(subscribedValue);
            didUpdate.set(newValue == subscribedValue);
          }
      );

      value.set(newValue);
      assertTrue(
          didUpdate.get(),
          "The subscriber should have been notified of the new value."
      );
      value.unsubscribeAll();
    }

    @Test
    @DisplayName("Setting to a similar value does not notify subscribers")
    void subscriptionsDontUpdateOnSimilar() {
      AtomicInteger updates = new AtomicInteger(0);
      SubscriptionHandler<Integer> value = new SubscriptionHandler<>(0);
      int newValue = value.get();

      value.subscribe(i -> updates.incrementAndGet());

      value.set(newValue);

      assertEquals(
          1,
          updates.get(),
          "The subscriber should not have been notified of a similar value."
      );
    }

    @Test
    @DisplayName("Unsubscribing removes the subscriber as a listener")
    void unsubscribingWorks() {
      AtomicBoolean didUpdate = new AtomicBoolean(false);
      SubscriptionHandler<Integer> value = new SubscriptionHandler<>(0);
      int newValue = 1;

      var unsubscribe = value.subscribe(subscribedValue ->
          didUpdate.set(newValue == subscribedValue)
      );

      unsubscribe.run();
      value.set(newValue);

      assertFalse(
          didUpdate.get(),
          "The subscriber should not have been notified after unsubscribing."
      );
    }
  }
}