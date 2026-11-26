/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FinalProject;

/**
 * The function implemented by this calculator:
 *
 * <pre>
 *   f(x)   = x³ − 2x² + x − 3
 *   f'(x)  = 3x² − 4x + 1
 * </pre>
 *
 * <p>The polynomial is fixed; the user only chooses the initial guess.</p>
 */
public class Hitung implements Rumus {

    @Override
    public double f(double x) {
        // x*x*x instead of Math.pow(x, 3): faster and more accurate.
        return x * x * x - 2 * x * x + x - 3;
    }

    @Override
    public double fPrime(double x) {
        return 3 * x * x - 4 * x + 1;
    }
}
