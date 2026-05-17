package bg.tu_varna.sit.f24621688.exceptions;

public class StudentNotFoundException extends StudentException {
    public StudentNotFoundException(String facultyNumber) {
        super("Student with FN '" + facultyNumber + "' not found.");
    }
}

