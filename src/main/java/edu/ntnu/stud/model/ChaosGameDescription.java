package edu.ntnu.stud.model;

import edu.ntnu.stud.model.math.TransformationGroup;
import edu.ntnu.stud.model.math.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * A record representing the starting conditions to a fractal display
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public record ChaosGameDescription(
    @NotNull Vector minCoords,
    @NotNull Vector maxCoords,
    @NotNull TransformationGroup transformations
) {}
