/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FinalProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The outcome of a Newton&ndash;Raphson computation: every performed iteration,
 * whether the method converged, the final root and a human-readable message.
 */
public final class NewtonSolution {

    private final List<NewtonIteration> iterations;
    private final boolean converged;
    private final double root;
    private final String message;

    public NewtonSolution(List<NewtonIteration> iterations, boolean converged, double root, String message) {
        this.iterations = Collections.unmodifiableList(new ArrayList<>(iterations));
        this.converged = converged;
        this.root = root;
        this.message = message;
    }

    /** @return the performed iterations, in order (never {@code null}). */
    public List<NewtonIteration> getIterations() {
        return iterations;
    }

    /** @return {@code true} if the iterations converged to the root. */
    public boolean isConverged() {
        return converged;
    }

    /** @return the last computed guess (the root when converged). */
    public double getRoot() {
        return root;
    }

    /** @return a human-readable summary of the result. */
    public String getMessage() {
        return message;
    }
}
