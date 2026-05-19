package bg.tu_varna.sit.f24621688.commands.student;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.CourseType;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.models.ExamRecord;
import bg.tu_varna.sit.f24621688.models.Program;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

/**
 * Executes the change command.
 * Changes the program, group, or year of an enrolled student.
 */
public class ChangeCommand extends BaseCommand {
    public ChangeCommand(AppSession session) { super(session); }

    /**
     * Executes the change command.
     * Supported options: {@code program}, {@code group}, {@code year}.
     *
     * @param args {@code args[1]} = faculty number, {@code args[2]} = option, {@code args[3+]} = value
     * @return a successful result or an error if the operation is not allowed
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 4) return CommandResult.error("Usage: change <fn> <option> <value>");

        String fn = args[1];
        String option = args[2].toLowerCase();
        Student student = getRepository().findStudentByFacultyNumber(fn);

        if (student == null) return CommandResult.error("Student " + fn + " not found.");
        if (student.getStatus() != StudentStatus.ENROLLED)
            return CommandResult.error("Student is not enrolled (status: " + student.getStatus() + ").");

        if (option.equals("program")) {
            StringBuilder pb = new StringBuilder();
            for (int i = 3; i < args.length; i++) {
                if (i > 3) pb.append(" ");
                pb.append(args[i]);
            }
            String newProg = pb.toString().replace("\"", "").trim();

            Program newProgram = getRepository().findProgramByName(newProg);
            if (newProgram == null) return CommandResult.error("Specialty '" + newProg + "' not found.");

            /** The student must not have more than 2 failed mandatory disciplines in the new program. */
            int failed = 0;
            for (Discipline d : newProgram.getDisciplines()) {
                if (d.getType() == CourseType.MANDATORY && d.getYear() <= student.getCourse()) {
                    ExamRecord rec = student.getGradeForDiscipline(d);
                    if (rec == null || !rec.isPassed()) failed++;
                }
            }
            if (failed > 2)
                return CommandResult.error("Cannot change specialty: " + failed + " mandatory subjects failed (max 2).");

            student.setSpecialty(newProgram);
            getSession().setHasUnsavedChanges(true);
            return CommandResult.success("Student " + fn + " changed specialty to " + newProg);

        } else if (option.equals("group")) {
            int newGroup;
            try { newGroup = Integer.parseInt(args[3]); }
            catch (NumberFormatException e) { return CommandResult.error("Group must be a number."); }
            if (newGroup <= 0) return CommandResult.error("Group must be positive.");

            student.setGroup(newGroup);
            getSession().setHasUnsavedChanges(true);
            return CommandResult.success("Student " + fn + " changed group to " + newGroup);

        } else if (option.equals("year")) {
            int newYear;
            try { newYear = Integer.parseInt(args[3]); }
            catch (NumberFormatException e) { return CommandResult.error("Year must be a number."); }

            if (newYear != student.getCourse() + 1)
                return CommandResult.error("Can only change to next year (year " + (student.getCourse() + 1) + ").");
            if (!student.canAdvance())
                return CommandResult.error("Too many failed mandatory subjects.");

            student.setCourse(newYear);
            getSession().setHasUnsavedChanges(true);
            return CommandResult.success("Student " + fn + " changed year to " + newYear);

        } else {
            return CommandResult.error("Invalid option. Use: program, group, or year.");
        }
    }
    @Override
    public String getName() {
        return "change";
    }

    @Override
    public String getUsage() {
        return "change <fn> <program|group|year> <value>";
    }

    @Override
    public String getDescription() {
        return "Changes student's program, group, or year";
    }
}
