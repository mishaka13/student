package bg.tu_varna.sit.f24621688.commands.specialty;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Program;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Executes the addspecialty command.
 Adds a new program (specialty) to the repository.
 */
public class AddSpecialtyCommand extends BaseCommand {

    public AddSpecialtyCommand(AppSession session) { super(session); }

    /**
     * Executes the addspecialty command.
     *
     * @param args program name and optional minimum credits
     * @return a successful result or an error
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: addspecialty \"<name>\" [minCredits]");


        int minCredits = 0;
        int nameEndIndex = args.length;

        try {
            minCredits = Integer.parseInt(args[args.length - 1]);
            nameEndIndex = args.length - 1;
        } catch (NumberFormatException e) {

        }

        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 1; i < nameEndIndex; i++) {
            if (i > 1) nameBuilder.append(" ");
            nameBuilder.append(args[i]);
        }
        String name = nameBuilder.toString().replace("\"", "").trim();

        if (name.isEmpty()) return CommandResult.error("Specialty name cannot be empty.");
        if (getRepository().findProgramByName(name) != null)
            return CommandResult.error("Specialty '" + name + "' already exists.");

        getRepository().addProgram(new Program(name, minCredits));
        getSession().setHasUnsavedChanges(true);
        return CommandResult.success("Specialty '" + name + "' added (minCredits=" + minCredits + ")");
    }

    @Override
    public String getName() {
        return "addspecialty";
    }

    @Override
    public String getUsage() {
        return "addspecialty \"<name>\" [minCredits]";
    }

    @Override
    public String getDescription() {
        return "Adds a new specialty/program";
    }
}
