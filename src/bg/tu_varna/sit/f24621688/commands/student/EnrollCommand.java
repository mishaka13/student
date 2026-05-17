package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Program;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

public class EnrollCommand extends BaseCommand {
    public EnrollCommand(AppSession session) { super(session); }

    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 5) return CommandResult.error("Usage: enroll <fn> \"<program>\" <group> \"<name>\"");

        String fn = args[1];

        int groupIndex = -1;
        for (int i = args.length - 1; i >= 2; i--) {
            try { Integer.parseInt(args[i]); groupIndex = i; break; }
            catch (NumberFormatException ignored) {}
        }

        if (groupIndex == -1 || groupIndex == args.length - 1)
            return CommandResult.error("Cannot find group number or student name is missing.");

        StringBuilder pb = new StringBuilder();
        for (int i = 2; i < groupIndex; i++) {
            if (i > 2) pb.append(" ");
            pb.append(args[i]);
        }
        String programName = pb.toString().replace("\"", "").trim();

        int group;
        try { group = Integer.parseInt(args[groupIndex]); }
        catch (NumberFormatException e) { return CommandResult.error("Group must be a number."); }
        if (group <= 0) return CommandResult.error("Group must be positive.");

        StringBuilder nb = new StringBuilder();
        for (int i = groupIndex + 1; i < args.length; i++) {
            if (i > groupIndex + 1) nb.append(" ");
            nb.append(args[i]);
        }
        String studentName = nb.toString().replace("\"", "").trim();
        if (studentName.isEmpty()) return CommandResult.error("Student name cannot be empty.");

        if (getRepository().findStudentByFacultyNumber(fn) != null)
            return CommandResult.error("Student with FN " + fn + " already exists.");

        Program program = getRepository().findProgramByName(programName);
        if (program == null) return CommandResult.error("Specialty '" + programName + "' does not exist.");

        Student student = new Student(studentName, fn, 1, program, group);
        getRepository().addStudent(student);
        getSession().setHasUnsavedChanges(true);

        return CommandResult.success("Enrolled: " + studentName + " (FN: " + fn + ") in " + programName + ", group " + group);
    }

    @Override
    public String getName() {
        return "enroll";
    }

    @Override
    public String getUsage() {
        return "enroll <fn> \"<program>\" <group> \"<name>\"";
    }

    @Override
    public String getDescription() {
        return "Enrolls a new student in year 1";
    }
}
