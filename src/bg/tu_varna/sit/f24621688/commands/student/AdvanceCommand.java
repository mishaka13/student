package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

public class AdvanceCommand extends BaseCommand {
    public AdvanceCommand(AppSession session) { super(session); }

    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: advance <fn>");

        String fn = args[1];
        Student student = getRepository().findStudentByFacultyNumber(fn);

        if (student == null) return CommandResult.error("Student " + fn + " not found.");
        if (student.getStatus() != StudentStatus.ENROLLED)
            return CommandResult.error("Student is not enrolled (status: " + student.getStatus() + ").");
        if (student.getCourse() >= 4)
            return CommandResult.error("Student is already in 4th year.");
        if (!student.canAdvance())
            return CommandResult.error("Too many failed mandatory subjects (max 2 allowed).");

        int old = student.getCourse();
        student.setCourse(old + 1);
        getSession().setHasUnsavedChanges(true);

        return CommandResult.success(student.getName() + " advanced from year " + old + " to " + student.getCourse());
    }

    @Override
    public String getName() {
        return "advance";
    }

    @Override
    public String getUsage() {
        return "advance <fn>";
    }

    @Override
    public String getDescription() {
        return "Advances student to next year";
    }
}
