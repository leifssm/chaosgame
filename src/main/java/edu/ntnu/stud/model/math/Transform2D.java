package edu.ntnu.stud.model.math;

import org.jetbrains.annotations.NotNull;

public interface Transform2D {
  @NotNull Vector transform(@NotNull Vector point);
}
