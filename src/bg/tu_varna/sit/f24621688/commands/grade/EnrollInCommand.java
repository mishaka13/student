package bg.tu_varna.sit.f24621688.commands.grade;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.models.ExamRecord;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;


/**
 * Registers a student in a specific discipline for their current academic year.
 */
public class EnrollInCommand extends BaseCommand {

    public EnrollInCommand(AppSession session) { super(session); }

    /**
     * Executes the enrollin command.
     * Validates five conditions before registering the student.
     *
     * @param args {@code args[1]} = faculty number, {@code args[2+]} = discipline name in quotes
     * @return a successful result or an error if any condition fails
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 3) return CommandResult.error("Usage: enrollin <fn> \"<discipline>\"");

        String fn = args[1];


        /** Collect the full discipline name from remaining tokens. */
        StringBuilder db = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            if (i > 2) db.append(" ");
            db.append(args[i]);
        }
        String disciplineName = db.toString();

        if (!disciplineName.startsWith("\"") || !disciplineName.endsWith("\""))
            return CommandResult.error("Discipline name must be in quotes: \"<discipline>\"");
        disciplineName = disciplineName.substring(1, disciplineName.length() - 1);

        Student student = getRepository().findStudentByFacultyNumber(fn);
        if (student == null) return CommandResult.error("Student " + fn + " not found.");
        if (student.getStatus() != StudentStatus.ENROLLED)
            return CommandResult.error("Student is not enrolled (status: " + student.getStatus() + ").");

        Discipline discipline = getRepository().findDisciplineByName(disciplineName);
        if (discipline == null) return CommandResult.error("Discipline '" + disciplineName + "' not found.");

        if (discipline.getYear() != student.getCourse())
            return CommandResult.error("Discipline is for year " + discipline.getYear()
                    + ", but student is in year " + student.getCourse());

        if (!student.getSpecialty().getDisciplines().contains(discipline))
            return CommandResult.error("Discipline '" + disciplineName + "' is not in specialty '" +
                    student.getSpecialty().getName() + "'.");

        if (student.getEnrolledDisciplines().contains(discipline))
            return CommandResult.error("Student already enrolled in " + disciplineName);
        for (ExamRecord rec : student.getGrades()) {
            if (rec.getDiscipline().equals(discipline))
                return CommandResult.error("Student already has a grade in " + disciplineName);
        }

        student.enrollInDiscipline(discipline);
        getSession().setHasUnsavedChanges(true);
        return CommandResult.success("Student " + fn + " enrolled in '" + disciplineName + "'.");
    }

    @Override
    public String getName() {
        return "enrollin";
    }

    @Override
    public String getUsage() {
        return "enrollin <fn> \"<discipline>\"";
    }

    @Override
    public String getDescription() {
        return "Enrolls student in a discipline";
    }
}
