package bg.tu_varna.sit.f24621688.commands.specialty;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Program;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.util.List;

/**
 * Executes the listspecialties command.
 Prints all programs currently in the repository.
 */
public class ListSpecialtiesCommand extends BaseCommand {
    public ListSpecialtiesCommand(AppSession session) { super(session); }

    /**
     * Executes the listspecialties command.
     *
     * @param args not used
     * @return a formatted list of all programs
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");

        List<Program> programs = getRepository().getAllPrograms();
        if (programs.isEmpty()) return CommandResult.success("No specialties registered.");

        StringBuilder sb = new StringBuilder("\nSpecialties:\n");
        for (Program p : programs) {
            sb.append("  - ").append(p.getName())
                    .append(" (minElectiveCredits=").append(p.getMinElectiveCredits()).append(")\n");
        }
        return CommandResult.success(sb.toString());
    }

    @Override
    public String getName() {
        return "listspecialties";
    }

    @Override
    public String getUsage() {
        return "listspecialties";
    }

    @Override
    public String getDescription() {
        return "Lists all specialties";
    }
}
