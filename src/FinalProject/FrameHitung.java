/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FinalProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Result screen: reveals the recorded Newton&ndash;Raphson iterations one by
 * one from the {@link NewtonSolution} passed in, without parsing label text
 * or duplicating the math.
 */
public class FrameHitung extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String[] COLUMNS = {"Iteration", "x", "F(x)", "F'(x)"};

    private final NewtonSolution solution;
    private final DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0);
    private final JLabel statusLabel = new JLabel();
    private final JLabel messageLabel = new JLabel();
    private final JButton nextIteration = new JButton("Next Iteration");
    private int cursor;

    /**
     * @param solution the computed Newton&ndash;Raphson solution to display
     */
    public FrameHitung(NewtonSolution solution) {
        super("Your Result");
        this.solution = solution;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildUi();
        showIteration(0);
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUi() {
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        statusLabel.setForeground(new Color(60, 60, 60));

        messageLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        messageLabel.setForeground(new Color(0, 100, 0));

        JTable table = new JTable(tableModel);
        table.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(520, 220));

        nextIteration.addActionListener(evt -> nextIterationActionPerformed());

        JButton back = new JButton("Change The Guess Root");
        back.addActionListener(evt -> {
            new HomeFrame().setVisible(true);
            dispose();
        });

        JButton close = new JButton("Finish and Close application");
        close.addActionListener(evt -> closeActionPerformed());

        JPanel buttons = new JPanel();
        buttons.add(nextIteration);
        buttons.add(back);
        buttons.add(close);

        JPanel south = new JPanel(new BorderLayout(0, 8));
        south.add(messageLabel, BorderLayout.CENTER);
        south.add(buttons, BorderLayout.SOUTH);

        JPanel content = new JPanel(new BorderLayout(8, 12));
        content.setBorder(new javax.swing.border.EmptyBorder(12, 12, 12, 12));
        content.add(statusLabel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(south, BorderLayout.SOUTH);
        setContentPane(content);
    }

    /**
     * Appends iteration {@code index} to the table and refreshes the status.
     */
    private void showIteration(int index) {
        List<NewtonIteration> iterations = solution.getIterations();
        NewtonIteration step = iterations.get(index);
        if (tableModel.getRowCount() <= index) {
            tableModel.addRow(new Object[]{
                step.getIteration(),
                format(step.getX()),
                format(step.getFx()),
                format(step.getFpx()),
            });
        }
        int shown = index + 1;
        int total = iterations.size();
        statusLabel.setText("Iteration " + shown + " / " + total);

        if (index == iterations.size() - 1) {
            messageLabel.setText(solution.getMessage());
            nextIteration.setEnabled(false);
        } else {
            messageLabel.setText("Click \"Next Iteration\" to continue.");
        }
    }

    private void nextIterationActionPerformed() {
        if (cursor < solution.getIterations().size() - 1) {
            cursor++;
            showIteration(cursor);
        }
    }

    private void closeActionPerformed() {
        int exit = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to close the application?",
                "Exit Warning!", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    private static String format(double value) {
        return String.format(Locale.US, "%.10g", value);
    }
}
