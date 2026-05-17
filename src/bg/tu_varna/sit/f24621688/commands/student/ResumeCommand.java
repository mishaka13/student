package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

public class ResumeCommand extends BaseCommand {
    public ResumeCommand(AppSession session) { super(session); }

    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: resume <fn>");

        String fn = args[1];
        Student student = getRepository().findStudentByFacultyNumber(fn);

        if (student == null) return CommandResult.error("Student " + fn + " not found.");
        if (student.getStatus() != StudentStatus.SUSPENDED)
            return CommandResult.error("Student is not suspended (status: " + student.getStatus() + ").");

        student.setStatus(StudentStatus.ENROLLED);
        getSession().setHasUnsavedChanges(true);
        return CommandResult.success(student.getName() + " (FN: " + fn + ") has been resumed.");
    }

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getUsage() {
        return "resume <fn>";
    }

    @Override
    public String getDescription() {
        return "Resumes a suspended student";
    }
}
