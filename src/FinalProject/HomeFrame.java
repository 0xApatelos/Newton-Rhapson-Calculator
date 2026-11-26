/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FinalProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Home screen: enter the initial guess for the root and start the
 * Newton&ndash;Raphson iteration. The computed {@link NewtonSolution} is handed
 * to {@link FrameHitung}; nothing is shared through static fields.
 */
public class HomeFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JTextField UserInput = new JTextField(16);
    private final JButton hitung = new JButton("CALCULATE");

    public HomeFrame() {
        super("Newton-Raphson Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildUi();
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUi() {
        JLabel title = new JLabel("NEWTON RAPHSON CALCULATOR", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 36));
        title.setForeground(new Color(120, 60, 120));

        JLabel subtitle = new JLabel("Find the root of  f(x) = x³ − 2x² + x − 3", SwingConstants.CENTER);
        subtitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JPanel formulas = new JPanel();
        formulas.setLayout(new GridBagLayout());
        formulas.add(formulaImage("As Example Given Function :", "/FinalProject/image2.png"),
                grid(0, 0, GridBagConstraints.WEST));
        formulas.add(formulaImage("Also the derivative             :", "/FinalProject/image.png"),
                grid(0, 1, GridBagConstraints.WEST));
        formulas.add(formulaImage("Newton-Raphson Formula :", "/FinalProject/image3.png"),
                grid(0, 2, GridBagConstraints.WEST));

        JLabel hint = new JLabel("Take a try to find the root, please insert your guess root",
                SwingConstants.CENTER);
        hint.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        UserInput.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        UserInput.setToolTipText("Enter your initial guess for x, e.g. 2");
        UserInput.setHorizontalAlignment(JTextField.CENTER);
        UserInput.addActionListener(evt -> calculate());

        hitung.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        hitung.addActionListener(evt -> calculate());

        JPanel inputRow = new JPanel();
        inputRow.add(hint);
        inputRow.add(UserInput);

        JPanel buttons = new JPanel();
        buttons.add(hitung);

        JPanel bottom = new JPanel(new BorderLayout(0, 8));
        bottom.add(inputRow, BorderLayout.CENTER);
        bottom.add(buttons, BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.add(subtitle, BorderLayout.NORTH);
        center.add(formulas, BorderLayout.CENTER);

        JPanel content = new JPanel(new BorderLayout(8, 12));
        content.setBorder(new javax.swing.border.EmptyBorder(16, 16, 16, 16));
        content.add(title, BorderLayout.NORTH);
        content.add(center, BorderLayout.CENTER);
        content.add(bottom, BorderLayout.SOUTH);

        setContentPane(content);
    }

    private static GridBagConstraints grid(int x, int y, int anchor) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.anchor = anchor;
        c.insets = new Insets(4, 0, 4, 16);
        return c;
    }

    private static JPanel formulaImage(String caption, String imagePath) {
        JLabel captionLabel = new JLabel(caption);
        captionLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 4));
        panel.add(captionLabel, BorderLayout.NORTH);
        ImageIcon icon = new ImageIcon(HomeFrame.class.getResource(imagePath));
        JLabel image = new JLabel(icon);
        image.setHorizontalAlignment(JLabel.CENTER);
        panel.add(image, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Parses and validates the user input, runs the Newton&ndash;Raphson
     * computation and opens the result frame.
     */
    private void calculate() {
        try {
            NewtonSolution solution = computeSolution(UserInput.getText());
            FrameHitung resultFrame = new FrameHitung(solution);
            resultFrame.setVisible(true);
            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
            UserInput.requestFocusInWindow();
            UserInput.selectAll();
        }
    }

    /**
     * Validates the raw text and returns the computed solution.
     *
     * @param rawGuess the text entered by the user
     * @return the Newton&ndash;Raphson solution
     * @throws IllegalArgumentException if the text is empty, not a number or
     *                                  not finite, or the solver reports a failure
     */
    NewtonSolution computeSolution(String rawGuess) {
        String text = rawGuess == null ? "" : rawGuess.trim().replace(',', '.');
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Please enter a number, e.g. 2.");
        }
        double guess;
        try {
            guess = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a valid number, e.g. 2 or 2.5.");
        }
        if (!Double.isFinite(guess)) {
            throw new IllegalArgumentException("The guess must be a finite number.");
        }
        try {
            return new NewtonSolver(new Hitung()).solve(guess);
        } catch (NewtonException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
}
