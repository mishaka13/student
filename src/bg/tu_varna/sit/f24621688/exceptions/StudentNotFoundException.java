package bg.tu_varna.sit.f24621688.exceptions;

/**
 * Thrown when a student with the given faculty number does not exist in the repository.
 */
public class StudentNotFoundException extends StudentException {
    /**
     * Constructs a {@code StudentNotFoundException} for the given faculty number.
     *
     * @param facultyNumber the faculty number that was not found
     */
    public StudentNotFoundException(String facultyNumber) {
        super("Student with FN '" + facultyNumber + "' not found.");
    }
}

