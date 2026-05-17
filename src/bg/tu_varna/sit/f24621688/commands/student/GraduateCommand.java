package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

public class GraduateCommand extends BaseCommand {
    public GraduateCommand(AppSession session) { super(session); }

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
