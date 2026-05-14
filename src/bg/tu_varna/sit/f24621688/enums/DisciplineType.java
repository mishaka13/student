package bg.tu_varna.sit.f24621688.enums;

import bg.tu_varna.sit.f24621688.exceptions.CommandException;

import java.io.Serializable;

/**
 * Represents whether an academic discipline is mandatory or elective.
 */
public enum DisciplineType implements Serializable {
    /** Mandatory discipline – required for advancing or graduating. */
    MANDATORY("mandatory"),
    /** Elective discipline – optional; chosen by the student. */
    ELECTIVE("elective");

    private final String type;

    DisciplineType(String type) {
        this.type = type;
    }

    /**
     * Returns the DisciplineType matching the given string value.
     * @param s the string to match.
     * @return the matching DisciplineType constant.
     * @throws CommandException if no match is found.
     */
    public static DisciplineType fromString(String s) {
        for (DisciplineType dt : values()) {
            if (dt.type.equalsIgnoreCase(s)) return dt;
        }
        throw new CommandException("Unknown discipline type: " + s + ". Valid: mandatory, elective");
    }

    @Override
    public String toString() {
        return type;
    }
}
