package edu.ntnu.stud.utils;

import java.util.ArrayList;

/**
 * A builder class for creating a string representation of an object.
 *
 * <p>
 *   Example usage:
 *   <pre>
 *     Vector v = new Vector(1, 2);
 *     return new ToStringBuilder(v)
 *       .field("x", v.getX0())
 *       .field("y", v.getX1())
 *       .build();
 *     // returns "Vector[x=1.0, y=2.0]"
 *   </pre>
 * </p>
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class ToStringBuilder {
  private final String objectName;
  private final ArrayList<String> result = new ArrayList<>();
  public ToStringBuilder(Object o) {
    objectName = o.getClass().getSimpleName() + "[";
  }

  public ToStringBuilder field(String name, Object value) {
    result.add(name + "=" + value);
    return this;
  }

  public String build() {
    return objectName + String.join(", ", result) + "]";
  }
}
