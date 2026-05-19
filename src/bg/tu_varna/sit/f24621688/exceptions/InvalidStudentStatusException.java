package bg.tu_varna.sit.f24621688.exceptions;

import bg.tu_varna.sit.f24621688.enums.StudentStatus;

/**
 * Thrown when an operation is not permitted for the student's current status.
 */
public class InvalidStudentStatusException extends StudentException {
    /**
     * Constructs an {@code InvalidStudentStatusException} with the current status.
     *
     * @param current the student's current status
     */
    public InvalidStudentStatusException(StudentStatus current) {
        /**
         * Constructs an {@code InvalidStudentStatusException} with the given message.
         *
         * @param message description of the error
         */
        super("Operation not allowed for student with status: " + current);
    }

    public InvalidStudentStatusException(String message) {
        super(message);
    }
}
