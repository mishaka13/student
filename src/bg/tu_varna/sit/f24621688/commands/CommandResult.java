package bg.tu_varna.sit.f24621688.commands;

/**
 * Wraps the result of a command execution.
 * Use  methods {@link #success(String)} and {@link #error(String)}.
 */
public class CommandResult {
    private final boolean success;
    private final String message;

    private CommandResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Creates a successful result with the given message.
     *
     * @param message the success message to display
     * @return a {@code CommandResult} with {@code success = true}
     */
    public static CommandResult success(String message) {
        return new CommandResult(true, message);
    }

    public static CommandResult error(String message) {
        String msg = message.startsWith("Error:") ? message : "Error: " + message;
        return new CommandResult(false, msg);
    }

    /**
     * Returns whether the command succeeded.
     *
     * @return {@code true} if the command was successful
     */
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
