package bg.tu_varna.sit.f24621688.commands;

import bg.tu_varna.sit.f24621688.commands.discipline.*;
import bg.tu_varna.sit.f24621688.commands.file.*;
import bg.tu_varna.sit.f24621688.commands.grade.*;
import bg.tu_varna.sit.f24621688.commands.other.*;
import bg.tu_varna.sit.f24621688.commands.specialty.*;
import bg.tu_varna.sit.f24621688.commands.student.*;
import bg.tu_varna.sit.f24621688.contracts.Command;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private final Map<String, Command> commandMap;

    public CommandParser(AppSession session) {
        this.commandMap = new HashMap<>();
        registerAll(session);
    }

    private void registerAll(AppSession session) {
        register(new OpenCommand(session));
        register(new SaveCommand(session));
        register(new SaveAsCommand(session));
        register(new CloseCommand(session));

        register(new AddSpecialtyCommand(session));
        register(new ListSpecialtiesCommand(session));
        register(new RemoveSpecialtyCommand(session));

        register(new AddDisciplineCommand(session));
        register(new ListDisciplinesCommand(session));
        register(new RemoveDisciplineCommand(session));

        register(new EnrollCommand(session));
        register(new PrintCommand(session));
        register(new PrintAllCommand(session));
        register(new AdvanceCommand(session));
        register(new GraduateCommand(session));
        register(new InterruptCommand(session));
        register(new ResumeCommand(session));
        register(new ChangeCommand(session));

        register(new EnrollInCommand(session));
        register(new AddGradeCommand(session));
        register(new ProtocolCommand(session));
        register(new ReportCommand(session));

        register(new ExitCommand());
        register(new HelpCommand(commandMap));
    }

    private void register(Command command) {
        commandMap.put(command.getName(), command);
    }

    public CommandResult parseAndExecute(String input) {
        if (input == null || input.trim().isEmpty()) {
            return CommandResult.success("");
        }

        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();

        Command command = commandMap.get(commandName);
        if (command == null) {
            return CommandResult.error("Unknown command: '" + commandName + "'. Type 'help'.");
        }

        return command.execute(parts);
    }
}

