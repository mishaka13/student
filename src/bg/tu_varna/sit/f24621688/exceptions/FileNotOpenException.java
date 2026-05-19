package bg.tu_varna.sit.f24621688.exceptions;

/**
 * Thrown when a command is executed but no file is currently open.
 */
public class FileNotOpenException extends StudentException {
    public FileNotOpenException() {
        super("No file is open. Use 'open <file.xml>' first.");
    }

    /**
     * Constructs a {@code FileNotOpenException} with the given message.
     *
     * @param message description of the error
     */
    public FileNotOpenException(String message) {
        super(message);
    }
}
