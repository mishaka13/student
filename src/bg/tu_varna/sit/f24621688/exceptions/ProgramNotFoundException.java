package bg.tu_varna.sit.f24621688.exceptions;

/**
 * Thrown when a program (specialty) with the given name does not exist in the repository.
 */
public class ProgramNotFoundException extends StudentException {
    /**
     * Constructs a {@code ProgramNotFoundException} for the given program name.
     *
     * @param name the program name that was not found
     */
    public ProgramNotFoundException(String name) {
        super("Specialty '" + name + "' not found.");
    }
}
