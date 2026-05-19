package bg.tu_varna.sit.f24621688.commands.specialty;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Program;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Executes the removespecialty command.
 Removes a program from the repository.
 */
public class RemoveSpecialtyCommand extends BaseCommand {
    public RemoveSpecialtyCommand(AppSession session) { super(session); }

    /**
     * Executes the removespecialty command.
     *
     * @param args the program name to remove
     * @return a successful result or an error if the program is not found
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: removespecialty \"<name>\"");

        StringBuilder nb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) nb.append(" ");
            nb.append(args[i]);
        }
        String name = nb.toString().replace("\"", "").trim();

        Program program = getRepository().findProgramByName(name);
        if (program == null) return CommandResult.error("Specialty '" + name + "' not found.");

        getRepository().removeProgram(program);
        getSession().setHasUnsavedChanges(true);
        return CommandResult.success("Specialty '" + name + "' removed.");
    }

    @Override
    public String getName() {
        return "removespecialty";
    }

    @Override
    public String getUsage() {
        return "removespecialty \"<name>\"";
    }

    @Override
    public String getDescription() {
        return "Removes a specialty";
    }
}
