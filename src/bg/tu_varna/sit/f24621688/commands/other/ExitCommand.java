package bg.tu_varna.sit.f24621688.commands.other;

import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.contracts.Command;

public class ExitCommand implements Command {
    @Override
    public CommandResult execute(String[] args) {
        return CommandResult.success("Exiting the program...");
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getUsage() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Exits the program";
    }
}

