package bg.tu_varna.sit.f24621688.enums;

import bg.tu_varna.sit.f24621688.exceptions.CommandException;

/**
 * Represents all commands supported by the Student Information System.
 */
public enum CommandType {
    OPEN("open"),
    CLOSE("close"),
    SAVE("save"),
    SAVEAS("saveas"),
    HELP("help"),
    EXIT("exit"),
    ENROLL("enroll"),
    ADVANCE("advance"),
    CHANGE("change"),
    GRADUATE("graduate"),
    INTERRUPT("interrupt"),
    RESUME("resume"),
    PRINT("print"),
    PRINTALL("printall"),
    ENROLLIN("enrollin"),
    ADDGRADE("addgrade"),
    PROTOCOL("protocol"),
    REPORT("report");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    /**
     * Returns the CommandType matching the given string.
     * @param command the command string typed by the user.
     * @return the matching CommandType constant.
     * @throws CommandException if the command is not recognised.
     */
    public static CommandType getCommand(String command) {
        for (CommandType c : values()) {
            if (c.command.equals(command)) return c;
        }
        throw new CommandException("Unknown command '" + command + "'. Type 'help' for a list of available commands.");
    }
}
