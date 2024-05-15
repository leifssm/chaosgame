package edu.ntnu.stud.utils;

import java.util.HashSet;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

/**
 * A class for flagging usages. Makes it possible for multiple classes to flag with a unique flag.
 * Allows grouping and handling of multiple related states. Can be used to for example show if any
 * calculations are running to display a loading spinner.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class UsageFlagger {

  /**
   * A set of all flags that are currently set.
   */
  private final HashSet<Integer> flags = new HashSet<>();
  /**
   * A handler for subscribing to flag changes.
   *
   * @see SubscriptionHandler
   */
  private final SubscriptionHandler<Boolean> subscriptions = new SubscriptionHandler<>(false);
  /**
   * Used for creating unique flags.
   */
  private volatile int flag = 0;

  /**
   * Creates a new flag group.
   */
  public UsageFlagger() {
  }

  /**
   * Toggles the flag.
   *
   * @param flag the flag id to toggle
   * @see #requestNewFlagId() Getting a flag id
   */
  public synchronized boolean toggleFlag(int flag) {
    if (isFlaggedBy(flag)) {
      unflag(flag);
      return false;
    } else {
      flag(flag);
      return true;
    }
  }

  /**
   * Checks if the group is flagged with the given flag.
   *
   * @param flag the flag id to query
   * @return true if the group is flagged with the given flag
   * @see #requestNewFlagId() Getting a flag id
   */
  public synchronized boolean isFlaggedBy(int flag) {
    return flags.contains(flag);
  }

  /**
   * Unflags the group with the given flag if present.
   *
   * @param flag the flag id to remove
   * @see #requestNewFlagId() Getting a flag id
   */
  public synchronized void unflag(int flag) {
    flags.remove(flag);
    subscriptions.set(isFlagged());
  }

  /**
   * Flags the group with the given flag.
   *
   * @param flag the flag id to set
   * @see #requestNewFlagId() Getting a flag id
   */
  public synchronized void flag(int flag) {
    flags.add(flag);
    subscriptions.set(true);
  }

  /**
   * Checks if the group is flagged by any flag.
   *
   * @return whether the group is flagged by any flag
   */
  public synchronized boolean isFlagged() {
    return !flags.isEmpty();
  }

  /**
   * @see SubscriptionHandler#subscribe(Consumer)
   */
  public @NotNull Runnable subscribe(@NotNull FlagListener listener) {
    return subscriptions.subscribe(listener::onFlagChange);
  }

  /**
   * @see SubscriptionHandler#unsubscribe(Consumer)
   */
  public boolean unsubscribe(@NotNull FlagListener subscription) {
    return subscriptions.unsubscribe(subscription::onFlagChange);
  }

  /**
   * Returns a flag setter for a new flag.
   *
   * @return a flag setter for a new flag
   */
  public @NotNull FlagSetter createFlagSetter() {
    return createFlagSetter(requestNewFlagId());
  }

  /**
   * Returns a flag setter for the given flag.
   *
   * @param flag the flag to change
   * @return a flag setter for the given flag
   */
  public @NotNull FlagSetter createFlagSetter(int flag) {
    return isFlagged -> {
      if (isFlagged) {
        flag(flag);
      } else {
        unflag(flag);
      }
    };
  }

  /**
   * Requests a new unique flag id.
   *
   * @return a new unique flag id to set or query
   */
  public synchronized int requestNewFlagId() {
    int flag = this.flag;
    this.flag++;
    return flag;
  }

  /**
   * A listener for global flag changes.
   */
  public interface FlagListener {

    /**
     * Called when the global state of flags changes.
     *
     * @param isFlagged Whether any flags are set.
     */
    void onFlagChange(boolean isFlagged);
  }

  /**
   * A setter for a unique flag.
   */
  public interface FlagSetter {

    /**
     * Sets the flag to flagged or not flagged.
     *
     * @param isFlagged whether the flag should be set or not
     */
    void setFlag(boolean isFlagged);
  }
}
