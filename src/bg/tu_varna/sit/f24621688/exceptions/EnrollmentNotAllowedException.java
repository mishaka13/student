package bg.tu_varna.sit.f24621688.exceptions;

/**
 * Thrown when a student is not allowed to enroll in a discipline.
 * Reasons may include wrong year, already enrolled, or existing grade.
 */
public class EnrollmentNotAllowedException extends StudentException {
    /**
     * Constructs an {@code EnrollmentNotAllowedException} with the given reason.
     *
     * @param reason the reason enrollment was refused
     */
    public EnrollmentNotAllowedException(String reason) {
        super("Enrollment not allowed: " + reason);
    }
}
