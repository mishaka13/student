package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Executes the interrupt command.
 * Suspends a student's studies by setting their status to {@link StudentStatus#SUSPENDED}.
 */
public class InterruptCommand extends BaseCommand {
    public InterruptCommand(AppSession session) { super(session); }

    /**
     * Executes the interrupt command.
     *
     * @param args the command arguments; {@code args[1]} is the faculty number
     * @return a successful result or an error if the student is not enrolled
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: interrupt <fn>");

        String fn = args[1];
        Student student = getRepository().findStudentByFacultyNumber(fn);

        if (student == null) return CommandResult.error("Student " + fn + " not found.");
        if (student.getStatus() != StudentStatus.ENROLLED)
            return CommandResult.error("Student is not enrolled (status: " + student.getStatus() + ").");

        student.setStatus(StudentStatus.SUSPENDED);
        getSession().setHasUnsavedChanges(true);
        return CommandResult.success(student.getName() + " (FN: " + fn + ") has been suspended.");
    }

    @Override
    public String getName() {
        return "interrupt";
    }

    @Override
    public String getUsage() {
        return "interrupt <fn>";
    }

    @Override
    public String getDescription() {
        return "Suspends a student's studies";
    }
}
