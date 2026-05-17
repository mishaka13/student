package bg.tu_varna.sit.f24621688.exceptions;

public class DisciplineNotFoundException extends StudentException {
    public DisciplineNotFoundException(String name) {
        super("Discipline '" + name + "' not found.");
    }
}
