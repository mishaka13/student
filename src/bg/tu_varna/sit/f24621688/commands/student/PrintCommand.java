package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Executes the print command.
 * Prints a summary of a single student's information.
 */
public class PrintCommand extends BaseCommand {

    public PrintCommand(AppSession session) { super(session); }

    /**
     * Executes the print command.
     *
     * @param args the command arguments; {@code args[1]} is the faculty number
     * @return a formatted student summary or an error if the student is not found
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: print <fn>");

        String fn = args[1];
        Student s = getRepository().findStudentByFacultyNumber(fn);
        if (s == null) return CommandResult.error("Student " + fn + " not found.");

        String info = "\nStudent Info:\n"
                + "-------------------\n"
                + "Name:              " + s.getName() + "\n"
                + "Faculty Number:    " + s.getFacultyNumber() + "\n"
                + "Specialty:         " + s.getSpecialty().getName() + "\n"
                + "Year:              " + s.getCourse() + "\n"
                + "Group:             " + s.getGroup() + "\n"
                + "Status:            " + s.getStatus() + "\n"
                + "GPA:               " + String.format("%.2f", s.getAverageGrade()) + "\n"
                + "Remaining Credits: " + s.getRemainingElectiveCredits() + "\n";

        return CommandResult.success(info);
    }

    @Override
    public String getName() {
        return "print";
    }

    @Override
    public String getUsage() {
        return "print <fn>";
    }

    @Override
    public String getDescription() {
        return "Prints student information";
    }
}
