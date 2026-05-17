package bg.tu_varna.sit.f24621688.exceptions;

public class FileNotOpenException extends StudentException {
    public FileNotOpenException() {
        super("No file is open. Use 'open <file.xml>' first.");
    }

    public FileNotOpenException(String message) {
        super(message);
    }
}
