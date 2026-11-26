/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FinalProject;

/**
 * Immutable record of one Newton&ndash;Raphson iteration step.
 */
public final class NewtonIteration {

    private final int iteration;
    private final double x;
    private final double fx;
    private final double fpx;
    private final double nextX;

    /**
     * @param iteration the iteration number (1-based)
     * @param x the current guess
     * @param fx the function value at {@code x}
     * @param fpx the derivative value at {@code x}
     * @param nextX the next guess, {@code x - fx / fpx}
     */
    public NewtonIteration(int iteration, double x, double fx, double fpx, double nextX) {
        this.iteration = iteration;
        this.x = x;
        this.fx = fx;
        this.fpx = fpx;
        this.nextX = nextX;
    }

    public int getIteration() {
        return iteration;
    }

    public double getX() {
        return x;
    }

    public double getFx() {
        return fx;
    }

    public double getFpx() {
        return fpx;
    }

    public double getNextX() {
        return nextX;
    }
}
