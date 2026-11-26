# Newton–Raphson Calculator

A small desktop teaching tool that demonstrates the **Newton–Raphson method** for
finding the root of a polynomial. It is a classic **NetBeans / Ant Swing**
project written for **JDK 21**, with a clean, well-tested solver core.

The application finds the root of the fixed polynomial

```
f(x) = x³ − 2x² + x − 3
f'(x) = 3x² − 4x + 1
```

Starting from a user-supplied initial guess, the tool iterates
`x_{n+1} = x_n − f(x_n) / f'(x_n)` and shows every step of the computation in a
table, so the convergence process is easy to follow.

For this function the root is **x ≈ 2.17455941029298** (converges from guesses
such as `2` or `0` within about 5 iterations).

## Features

- **Interactive GUI** — enter an initial guess, press *Calculate*, and watch the
  iterations unfold one by one with the *Next Iteration* button.
- **Robust solver** — validates input, detects zero derivatives
  (`f'(x) = 0`, which occurs at `x = 1`), reports divergence, and caps the
  number of iterations.
- **Immutable, well-documented core** — the mathematics is separated from the UI:
  - `Rumus` — the function contract (`f` and `f'`)
  - `Hitung` — the fixed polynomial implementation
  - `NewtonSolver` — the iteration engine with guards and recording
  - `NewtonIteration` / `NewtonSolution` — immutable per-step and final results
  - `NewtonException` — checked failure signal
- **Self-contained test suite** — no external test framework required; the tests
  run with a plain `main` and exit non-zero on failure.

## Requirements

- **JDK 21** (the project targets Java 21: `javac.source=21`, `javac.target=21`)
- **Apache Ant** (only needed for the CLI build; NetBeans bundles its own Ant)

## Getting started

### With NetBeans (recommended)

1. Clone the repository.
2. Open the project in **Apache NetBeans** (File → Open Project…).
3. Build with **Run → Build Project** (F6).
4. Run with **Run Project** (F6 with the *Run* profile, or the Run toolbar button).
   The `Main-Class` is configured in `nbproject/project.properties`
   (`main.class=FinalProject.Main`).

### From the command line (Ant)

```bash
ant compile        # compile sources into build/classes
ant run            # compile and launch the application
ant clean          # remove build output
```

### From the command line (plain javac / java)

```bash
# Compile the application sources
mkdir -p build/classes
javac -d build/classes src/FinalProject/*.java

# Run the GUI
java -cp build/classes FinalProject.Main

# Compile and run the test suite (needs the app classes on the classpath)
mkdir -p build/test-classes
javac -cp build/classes -d build/test-classes test/FinalProject/*.java
java -cp build/classes:build/test-classes FinalProject.NewtonSolverTest
```

The test suite prints one `PASS`/`FAIL` line per check and exits with a non-zero
status if anything fails.

## Project structure

```
src/FinalProject/
    Main.java            application entry point (sets the Nimbus look and feel)
    HomeFrame.java       home window: guess input + formulas
    FrameHitung.java     result window: step-by-step iteration table
    Rumus.java           function contract: f(x) and f'(x)
    Hitung.java          f(x) = x³ − 2x² + x − 3, f'(x) = 3x² − 4x + 1
    NewtonSolver.java    iteration engine with validation and step recording
    NewtonIteration.java immutable record of one iteration step
    NewtonSolution.java  immutable result (iterations, convergence, root)
    NewtonException.java checked exception for solver failures
    image.png, image2.png, image3.png   formula images shown on the home window
test/FinalProject/
    NewtonSolverTest.java dependency-free test suite for the solver
nbproject/             NetBeans project configuration
build.xml              Ant build file
manifest.mf            JAR manifest (Main-Class: FinalProject.Main)
```

## How the solver behaves

- The initial guess, tolerance, and maximum iteration count are validated;
  non-finite values are rejected with `IllegalArgumentException`.
- If `f'(x) = 0` at any step, the solver throws `NewtonException`
  (message contains `F'(x) = 0`).
- If the iteration diverges (non-finite values), the solver throws
  `NewtonException` (message contains `diverged`).
- The iteration stops when the step size `|Δx|` drops below the tolerance
  (default `1e-9`) or the maximum number of iterations (default `100`) is
  reached. A non-converged run still returns the recorded steps with
  `isConverged() == false`.

## License

This project is released under the **MIT License** — see [LICENSE](LICENSE).
