/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FinalProject;

import java.util.ArrayList;
import java.util.List;

/**
 * Runs the Newton&ndash;Raphson iteration for a {@link Rumus} and records every
 * step, so the GUI can reveal iterations one by one without re-parsing or
 * duplicating the math.
 *
 * <p>All failure modes are reported explicitly instead of silently producing
 * {@code Infinity}/{@code NaN}:</p>
 * <ul>
 *   <li>non-finite guess   &rarr; {@link IllegalArgumentException}</li>
 *   <li>zero derivative    &rarr; {@link NewtonException}</li>
 *   <li>divergence         &rarr; {@link NewtonException}</li>
 *   <li>no convergence     &rarr; a {@link NewtonSolution} with {@code converged == false}</li>
 * </ul>
 */
public final class NewtonSolver {

    /** Absolute tolerance on the step size |x(n+1) - x(n)|. */
    public static final double DEFAULT_TOLERANCE = 1e-9;

    /** Safety cap on the number of iterations. */
    public static final int DEFAULT_MAX_ITERATIONS = 100;

    private final Rumus rumus;

    public NewtonSolver(Rumus rumus) {
        this.rumus = rumus;
    }

    /**
     * Solves {@code rumus} starting from {@code guess} with the default
     * tolerance and iteration cap.
     *
     * @param guess the initial guess for the root
     * @return the solution, containing every performed iteration
     * @throws IllegalArgumentException if the guess is not a finite number
     * @throws NewtonException if the derivative is zero or the iteration diverges
     */
    public NewtonSolution solve(double guess) throws NewtonException {
        return solve(guess, DEFAULT_TOLERANCE, DEFAULT_MAX_ITERATIONS);
    }

    /**
     * Solves {@code rumus} starting from {@code guess}.
     *
     * @param guess the initial guess for the root
     * @param tolerance convergence threshold on the step size
     * @param maxIterations maximum number of iterations to perform
     * @return the solution, containing every performed iteration
     * @throws IllegalArgumentException if an argument is invalid
     * @throws NewtonException if the derivative is zero or the iteration diverges
     */
    public NewtonSolution solve(double guess, double tolerance, int maxIterations) throws NewtonException {
        if (!Double.isFinite(guess)) {
            throw new IllegalArgumentException("The guess must be a finite number, got: " + guess);
        }
        if (!Double.isFinite(tolerance) || tolerance <= 0) {
            throw new IllegalArgumentException("The tolerance must be a positive finite number.");
        }
        if (maxIterations < 1) {
            throw new IllegalArgumentException("The maximum number of iterations must be at least 1.");
        }

        List<NewtonIteration> iterations = new ArrayList<>();
        double x = guess;

        for (int i = 1; i <= maxIterations; i++) {
            double fx = rumus.f(x);
            double fpx = rumus.fPrime(x);

            if (!Double.isFinite(fx) || !Double.isFinite(fpx)) {
                throw new NewtonException("Iteration " + i
                        + " diverged: F(x) or F'(x) is not a finite number (x = " + x + ").");
            }
            if (fpx == 0.0) {
                throw new NewtonException("F'(x) = 0 at x = " + x + " (iteration " + i
                        + "). Newton-Raphson cannot continue from this guess.");
            }

            double nextX = x - fx / fpx;
            if (!Double.isFinite(nextX)) {
                throw new NewtonException("Iteration " + i
                        + " diverged: the next guess is not a finite number (x = " + x + ").");
            }

            iterations.add(new NewtonIteration(i, x, fx, fpx, nextX));

            if (Math.abs(nextX - x) < tolerance) {
                return new NewtonSolution(iterations, true, nextX,
                        "Converged to x = " + nextX + " in " + i + " iteration(s).");
            }
            x = nextX;
        }

        return new NewtonSolution(iterations, false, x,
                "Did not converge within " + maxIterations + " iterations. Try a different guess.");
    }
}
