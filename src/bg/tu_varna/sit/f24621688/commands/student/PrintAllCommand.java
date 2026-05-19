package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Executes the printall command.
 * Prints all actively enrolled students in the given program and academic year.
 */
public class PrintAllCommand extends BaseCommand {

    public PrintAllCommand(AppSession session) { super(session); }

    /**
     * Executes the printall command.
     *
     * @param args tokens from the input; the last token is the year, the rest form the program name
     * @return a formatted student list or an error if arguments are invalid
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 3) return CommandResult.error("Usage: printall \"<program>\" <year>");

        int year;
        try { year = Integer.parseInt(args[args.length - 1]); }
        catch (NumberFormatException e) { return CommandResult.error("Year must be a number."); }

        StringBuilder pb = new StringBuilder();
        for (int i = 1; i < args.length - 1; i++) {
            if (i > 1) pb.append(" ");
            pb.append(args[i]);
        }
        String programName = pb.toString().replace("\"", "").trim();

        List<Student> filtered = new ArrayList<>();
        for (Student s : getRepository().getAllStudents()) {
            if (s.getStatus() == StudentStatus.ENROLLED
                    && s.getCourse() == year
                    && s.getSpecialty().getName().equalsIgnoreCase(programName)) {
                filtered.add(s);
            }
        }

        if (filtered.isEmpty())
            return CommandResult.success("No enrolled students in " + programName + ", year " + year);

        StringBuilder sb = new StringBuilder("\nStudents in " + programName + " - Year " + year + ":\n");
        sb.append("------------------------------------------\n");
        for (Student s : filtered) {
            sb.append(s.getFacultyNumber()).append(" | ")
                    .append(s.getName()).append(" | Group ").append(s.getGroup()).append("\n");
        }
        return CommandResult.success(sb.toString());
    }

    @Override
    public String getName() {
        return "printall";
    }

    @Override
    public String getUsage() {
        return "printall \"<program>\" <year>";
    }

    @Override
    public String getDescription() {
        return "Prints all enrolled students in a program/year";
    }
}
