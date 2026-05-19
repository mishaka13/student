package bg.tu_varna.sit.f24621688.exceptions;

/**
 * Base exception for all errors in the student information system.
 */
public class StudentException extends RuntimeException {
    /**
     * Constructs a {@code StudentException} with the given message.
     *
     * @param message description of the error
     */
    public StudentException(String message) {
        super(message);
    }
}
