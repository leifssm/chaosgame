package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.TransformationGroup;
import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * A record representing the starting conditions to a fractal display.
 *
 * @param minCoords the bottom left bounds of the fractal display
 * @param maxCoords the top right bounds of the fractal display
 * @param transformations the transformations used to generate the fractal
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public record ChaosGameDescription(
    @NotNull Vector minCoords,
    @NotNull Vector maxCoords,
    @NotNull TransformationGroup transformations
) {}
