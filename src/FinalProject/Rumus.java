/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package FinalProject;

/**
 * Contract for a function used with the Newton&ndash;Raphson method.
 *
 * <p>Implementations only need to provide the function {@link #f(double)} and
 * its derivative {@link #fPrime(double)}; the iteration itself is performed
 * by {@link NewtonSolver}.</p>
 */
public interface Rumus {

    /**
     * Evaluates the function at {@code x}.
     *
     * @param x the point to evaluate
     * @return the value of the function at {@code x}
     */
    double f(double x);

    /**
     * Evaluates the derivative of the function at {@code x}.
     *
     * @param x the point to evaluate
     * @return the value of the derivative at {@code x}
     */
    double fPrime(double x);
}
