package bg.tu_varna.sit.f24621688.commands.discipline;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.session.AppSession;

public class RemoveDisciplineCommand extends BaseCommand {
    public RemoveDisciplineCommand(AppSession session) { super(session); }

    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: removediscipline \"<name>\"");

        StringBuilder nb = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) nb.append(" ");
            nb.append(args[i]);
        }
        String name = nb.toString().replace("\"", "").trim();

        Discipline discipline = getRepository().findDisciplineByName(name);
        if (discipline == null) return CommandResult.error("Discipline '" + name + "' not found.");

        getRepository().removeDiscipline(discipline);
        getSession().setHasUnsavedChanges(true);
        return CommandResult.success("Discipline '" + name + "' removed.");
    }

    @Override
    public String getName() {
        return "removediscipline";
    }

    @Override
    public String getUsage() {
        return "removediscipline \"<name>\"";
    }

    @Override
    public String getDescription() {
        return "Removes a discipline";
    }
}
