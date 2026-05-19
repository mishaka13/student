package bg.tu_varna.sit.f24621688.commands.discipline;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.util.List;

/**
 * Executes the listdisciplines command.
 Prints all disciplines in the repository.
 */
public class ListDisciplinesCommand extends BaseCommand {
    public ListDisciplinesCommand(AppSession session) { super(session); }

    /**
     * Executes the listdisciplines command.
     *
     * @param args not used
     * @return a formatted list of all disciplines
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");

        List<Discipline> list = getRepository().getAllDisciplines();
        if (list.isEmpty()) return CommandResult.success("No disciplines registered.");

        StringBuilder sb = new StringBuilder("\nDisciplines:\n");
        for (Discipline d : list) {
            sb.append("  - ").append(d.getName())
                    .append(" [").append(d.getType()).append("]")
                    .append(" year=").append(d.getYear())
                    .append(" credits=").append(d.getCredits()).append("\n");
        }
        return CommandResult.success(sb.toString());
    }

    @Override
    public String getName() {
        return "listdisciplines";
    }

    @Override
    public String getUsage() {
        return "listdisciplines";
    }

    @Override
    public String getDescription() {
        return "Lists all disciplines";
    }
}
