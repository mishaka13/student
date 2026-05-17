package bg.tu_varna.sit.f24621688.exceptions;

import bg.tu_varna.sit.f24621688.enums.StudentStatus;

public class InvalidStudentStatusException extends StudentException {
    public InvalidStudentStatusException(StudentStatus current) {
        super("Operation not allowed for student with status: " + current);
    }

    public InvalidStudentStatusException(String message) {
        super(message);
    }
}
