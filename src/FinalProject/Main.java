/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FinalProject;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Application entry point. Opens the {@link HomeFrame} where the user enters
 * the initial guess for the Newton&ndash;Raphson iteration.
 */
public final class Main {

    private Main() {
        // utility class, not meant to be instantiated
    }

    public static void main(String args[]) {
        installNimbusLookAndFeel();
        java.awt.EventQueue.invokeLater(() -> new HomeFrame().setVisible(true));
    }

    /**
     * Switches to the Nimbus look and feel when it is available and stays
     * with the platform default otherwise.
     */
    private static void installNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            // Falling back to the default look and feel is not fatal.
            System.err.println("Could not install the Nimbus look and feel: " + ex.getMessage());
        }
    }
}
