package bg.tu_varna.sit.f24621688.exceptions;

public class EnrollmentNotAllowedException extends StudentException {
    public EnrollmentNotAllowedException(String reason) {
        super("Enrollment not allowed: " + reason);
    }
}
