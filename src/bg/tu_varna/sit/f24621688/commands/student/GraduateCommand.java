package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Executes the graduate command.
 * Marks a student as graduated if all requirements are fulfilled.
 */
public class GraduateCommand extends BaseCommand {
    public GraduateCommand(AppSession session) { super(session); }

    /**
     * Executes the graduate command.
     * The student must have passed all registered disciplines and earned enough elective credits.
     *
     * @param args the command arguments; {@code args[1]} is the faculty number
     * @return a successful result or an error if graduation requirements are not met
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: graduate <fn>");

        String fn = args[1];
        Student student = getRepository().findStudentByFacultyNumber(fn);

        if (student == null) return CommandResult.error("Student " + fn + " not found.");
        if (student.getStatus() != StudentStatus.ENROLLED)
            return CommandResult.error("Student is not enrolled (status: " + student.getStatus() + ").");

        if (!student.canGraduate()) {
            String reason;
            if (student.getRemainingElectiveCredits() > 0)
                reason = "Missing " + student.getRemainingElectiveCredits() + " elective credits.";
            else
                reason = "Not all enrolled disciplines are passed.";
            return CommandResult.error("Cannot graduate: " + reason);
        }

        student.setStatus(StudentStatus.GRADUATED);
        getSession().setHasUnsavedChanges(true);
        return CommandResult.success(student.getName() + " (FN: " + fn + ") has graduated!");
    }

    @Override
    public String getName() {
        return "graduate";
    }

    @Override
    public String getUsage() {
        return "graduate <fn>";
    }

    @Override
    public String getDescription() {
        return "Marks a student as graduated";
    }
}
