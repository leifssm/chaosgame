package edu.ntnu.stud.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * A builder class for creating a string representation of an object.
 *
 * <p>
 * Example usage:
 * <pre>
 *     Vector v = new Vector(1, 2);
 *     return new ToStringBuilder(v)
 *       .field("x0", v.getX0())
 *       .field("x1", v.getX1())
 *       .build();
 *     // returns "Vector[x0=1.0, x1=2.0]"
 *   </pre>
 * </p>
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ToStringBuilder {

  /**
   * The name of the object to be stringified.
   */
  private final @NotNull String objectName;

  /**
   * The fields of the object to be stringified.
   */
  private final @NotNull ArrayList<String> result = new ArrayList<>();

  /**
   * Creates a new instance with the given object to stringify.
   *
   * @param o the object to stringify
   * @throws IllegalArgumentException if the object is null
   */
  public ToStringBuilder(@NotNull Object o) throws IllegalArgumentException {
    objectName = o.getClass().getSimpleName() + "[";
  }

  /**
   * Adds a field to the builder which will be displayed.
   *
   * @param name  the name of the field
   * @param value the value of the field
   * @return this builder
   */
  public @NotNull ToStringBuilder field(@NotNull String name, @Nullable Object value) {
    result.add(name + "=" + value);
    return this;
  }

  /**
   * Builds the string and returns it.
   *
   * @return the string representation of the object
   */
  public @NotNull String build() {
    return objectName + String.join(", ", result) + "]";
  }
}
