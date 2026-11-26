/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FinalProject;

/**
 * Thrown when a Newton&ndash;Raphson computation cannot produce a valid root,
 * e.g. when the derivative is zero at the current guess or when the iteration
 * diverges to a non-finite value.
 */
public class NewtonException extends Exception {

    private static final long serialVersionUID = 1L;

    public NewtonException(String message) {
        super(message);
    }

    public NewtonException(String message, Throwable cause) {
        super(message, cause);
    }
}
