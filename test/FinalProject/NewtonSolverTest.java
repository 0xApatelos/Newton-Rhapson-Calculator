/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FinalProject;

import java.util.List;

/**
 * Self-contained test suite for the Newton&ndash;Raphson solver. Runs without
 * external dependencies:
 *
 * <pre>
 *   javac -d build/test/classes -cp build/classes test/FinalProject/NewtonSolverTest.java
 *   java -cp build/classes:build/test/classes FinalProject.NewtonSolverTest
 * </pre>
 *
 * <p>Exits with a non-zero status if any test fails.</p>
 */
public final class NewtonSolverTest {

    private static int passed;
    private static int failed;

    private NewtonSolverTest() {
        // utility class
    }

    public static void main(String[] args) {
        testConvergenceFromGuessTwo();
        testConvergenceFromZero();
        testZeroDerivativeThrows();
        testNonFiniteGuessThrows();
        testHugeGuessDiverges();
        testNoConvergenceWithSmallCap();
        testHitungPolynomialValues();
        testSolutionExposesAllIterations();

        System.out.println();
        System.out.println("Passed: " + passed + ", Failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testConvergenceFromGuessTwo() {
        // The polynomial x³ - 2x² + x - 3 has a real root ≈ 2.17455941029298.
        // Guessing 2 must converge to it quickly.
        try {
            NewtonSolution solution = new NewtonSolver(new Hitung()).solve(2.0);
            check("solve(2.0) converges", solution.isConverged());
            check("root ≈ 2.17455941029298",
                    Math.abs(solution.getRoot() - 2.17455941029298) < 1e-9);
            int count = solution.getIterations().size();
            check("converges within 10 iterations (got " + count + ")", count <= 10 && count >= 3);
        } catch (NewtonException ex) {
            check("solve(2.0) should not throw: " + ex.getMessage(), false);
        }
    }

    private static void testConvergenceFromZero() {
        // A different valid guess must find the same root.
        try {
            NewtonSolution solution = new NewtonSolver(new Hitung()).solve(0.0);
            check("solve(0.0) converges", solution.isConverged());
            check("same root as guess 2",
                    Math.abs(solution.getRoot() - 2.17455941029298) < 1e-9);
        } catch (NewtonException ex) {
            check("solve(0.0) should not throw: " + ex.getMessage(), false);
        }
    }

    private static void testZeroDerivativeThrows() {
        // f'(x) = 3x² - 4x + 1 is exactly 0 at x = 1.
        try {
            new NewtonSolver(new Hitung()).solve(1.0);
            check("solve(1.0) must throw (f'(1) = 0)", false);
        } catch (NewtonException expected) {
            check("solve(1.0) reports zero derivative",
                    expected.getMessage().contains("F'(x) = 0"));
        }
    }

    private static void testNonFiniteGuessThrows() {
        try {
            new NewtonSolver(new Hitung()).solve(Double.NaN);
            check("solve(NaN) must throw", false);
        } catch (Exception expected) {
            check("solve(NaN) rejected as IllegalArgumentException",
                    expected instanceof IllegalArgumentException);
        }
        try {
            new NewtonSolver(new Hitung()).solve(Double.POSITIVE_INFINITY);
            check("solve(Infinity) must throw", false);
        } catch (Exception expected) {
            check("solve(Infinity) rejected as IllegalArgumentException",
                    expected instanceof IllegalArgumentException);
        }
    }

    private static void testHugeGuessDiverges() {
        // A huge guess overflows F(x) to Infinity; the solver must report it
        // instead of silently producing Infinity/NaN "roots".
        try {
            new NewtonSolver(new Hitung()).solve(1e200);
            check("solve(1e200) must throw (overflow)", false);
        } catch (NewtonException expected) {
            check("solve(1e200) reports divergence",
                    expected.getMessage().toLowerCase().contains("diverged"));
        }
    }

    private static void testNoConvergenceWithSmallCap() {
        // With a tiny iteration cap the solver must report non-convergence
        // as a result state, not an exception.
        try {
            NewtonSolution solution = new NewtonSolver(new Hitung()).solve(2.0, 1e-15, 1);
            check("single-step run does not throw", true);
            check("single-step run reports non-convergence", !solution.isConverged());
            check("single-step run has exactly one iteration",
                    solution.getIterations().size() == 1);
        } catch (NewtonException ex) {
            check("single-step run should not throw: " + ex.getMessage(), false);
        }
    }

    private static void testHitungPolynomialValues() {
        Hitung hitung = new Hitung();
        check("f(2) = -1", hitung.f(2.0) == -1.0);
        check("f'(2) = 5", hitung.fPrime(2.0) == 5.0);
        check("f(1) = -3", hitung.f(1.0) == -3.0);
        check("f'(1) = 0", hitung.fPrime(1.0) == 0.0);
    }

    private static void testSolutionExposesAllIterations() {
        try {
            NewtonSolution solution = new NewtonSolver(new Hitung()).solve(2.0);
            List<NewtonIteration> iterations = solution.getIterations();
            check("iteration numbers are 1-based and ordered",
                    iterations.get(0).getIteration() == 1
                    && iterations.get(iterations.size() - 1).getIteration()
                       == iterations.size());
            NewtonIteration first = iterations.get(0);
            check("first iteration starts at the guess", first.getX() == 2.0);
            check("first next guess is 2.2", Math.abs(first.getNextX() - 2.2) < 1e-12);
            check("iteration values are finite",
                    Double.isFinite(first.getFx()) && Double.isFinite(first.getFpx()));
        } catch (NewtonException ex) {
            check("should not throw: " + ex.getMessage(), false);
        }
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS  " + name);
        } else {
            failed++;
            System.out.println("FAIL  " + name);
        }
    }
}
