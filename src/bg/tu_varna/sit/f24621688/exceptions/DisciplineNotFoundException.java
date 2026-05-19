package bg.tu_varna.sit.f24621688.exceptions;

/**
 * Thrown when a discipline with the given name does not exist in the repository.
 */
public class DisciplineNotFoundException extends StudentException {
    /**
     * Constructs a {@code DisciplineNotFoundException} for the given discipline name.
     *
     * @param name the discipline name that was not found
     */
    public DisciplineNotFoundException(String name) {
        super("Discipline '" + name + "' not found.");
    }
}
