package bg.tu_varna.sit.f24621688.contracts;

import bg.tu_varna.sit.f24621688.commands.CommandResult;

public interface Command {
    CommandResult execute(String[] args);
    String getName();
    String getUsage();
    String getDescription();
}
