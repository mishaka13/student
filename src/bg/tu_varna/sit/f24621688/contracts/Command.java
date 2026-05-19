package bg.tu_varna.sit.f24621688.contracts;

import bg.tu_varna.sit.f24621688.commands.CommandResult;

/**
 * Base interface for all command types.
 */
public interface Command {
    /**
     * Executes the command with the given argument tokens.
     *
     * @param args the full token array; {@code args[0]} is the command keyword
     * @return a {@link CommandResult} containing a success flag and a message
     */
    CommandResult execute(String[] args);
    String getName();
    String getUsage();
    String getDescription();
}
