package bg.tu_varna.sit.f24621688.commands.discipline;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.CourseType;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.models.Program;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Executes the adddiscipline command.
 *Adds a new discipline and links it to all existing programs.
 */

public class AddDisciplineCommand extends BaseCommand {
    public AddDisciplineCommand(AppSession session) { super(session); }

    /**
     * @param args name, type (MANDATORY|ELECTIVE), credits, year
     * @return a successful result or an error
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 5) return CommandResult.error("Usage: adddiscipline \"<name>\" <MANDATORY|ELECTIVE> <credits> <year>");

        int year, credits;
        CourseType type;
        try {
            year    = Integer.parseInt(args[args.length - 1]);
            credits = Integer.parseInt(args[args.length - 2]);
            type    = CourseType.valueOf(args[args.length - 3].toUpperCase());
        } catch (Exception e) {
            return CommandResult.error("Usage: adddiscipline \"<name>\" <MANDATORY|ELECTIVE> <credits> <year>");
        }

        if (year < 1 || year > 4) return CommandResult.error("Year must be between 1 and 4.");

        StringBuilder nb = new StringBuilder();
        for (int i = 1; i < args.length - 3; i++) {
            if (i > 1) nb.append(" ");
            nb.append(args[i]);
        }
        String name = nb.toString().replace("\"", "").trim();
        if (name.isEmpty()) return CommandResult.error("Discipline name cannot be empty.");
        if (getRepository().findDisciplineByName(name) != null)
            return CommandResult.error("Discipline '" + name + "' already exists.");

        Discipline discipline = new Discipline(name, type, year);
        discipline.setCredits(credits);
        getRepository().addDiscipline(discipline);

        for (Program p : getRepository().getAllPrograms()) {
            p.addDiscipline(discipline);
        }

        getSession().setHasUnsavedChanges(true);
        return CommandResult.success("Discipline '" + name + "' (" + type + ", year=" + year + ") added.");
    }

    @Override
    public String getName() {
        return "adddiscipline";
    }

    @Override
    public String getUsage() {
        return "adddiscipline \"<name>\" <MANDATORY|ELECTIVE> <credits> <year>";
    }

    @Override
    public String getDescription() {
        return "Adds a new discipline";
    }
}
