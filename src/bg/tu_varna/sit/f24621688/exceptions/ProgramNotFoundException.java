package bg.tu_varna.sit.f24621688.exceptions;

public class ProgramNotFoundException extends StudentException {
    public ProgramNotFoundException(String name) {
        super("Specialty '" + name + "' not found.");
    }
}
