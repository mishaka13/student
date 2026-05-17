package bg.tu_varna.sit.f24621688.commands.grade;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.models.ExamRecord;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

public class AddGradeCommand extends BaseCommand {

    public AddGradeCommand(AppSession session) { super(session); }

    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 4) return CommandResult.error("Usage: addgrade <fn> \"<discipline>\" <grade>");

        String fn = args[1];

        double score;
        try { score = Double.parseDouble(args[args.length - 1]); }
        catch (NumberFormatException e) { return CommandResult.error("Grade must be a number."); }

        if (score < 2.00 || score > 6.00)
            return CommandResult.error("Grade must be between 2.00 and 6.00.");

        StringBuilder db = new StringBuilder();
        for (int i = 2; i < args.length - 1; i++) {
            if (i > 2) db.append(" ");
            db.append(args[i]);
        }
        String disciplineName = db.toString().replace("\"", "").trim();

        Student student = getRepository().findStudentByFacultyNumber(fn);
        if (student == null) return CommandResult.error("Student " + fn + " not found.");
        if (student.getStatus() != StudentStatus.ENROLLED)
            return CommandResult.error("Student is not enrolled.");

        Discipline discipline = getRepository().findDisciplineByName(disciplineName);
        if (discipline == null) return CommandResult.error("Discipline '" + disciplineName + "' not found.");

        if (!student.getEnrolledDisciplines().contains(discipline))
            return CommandResult.error("Student is not enrolled in '" + disciplineName + "'. Use 'enrollin' first.");

        if (student.getGradeForDiscipline(discipline) != null)
            return CommandResult.error("Student already has a grade in '" + disciplineName + "'.");

        ExamRecord record = new ExamRecord(discipline, score);
        student.addGrade(record);
        getSession().setHasUnsavedChanges(true);

        String result = (score >= 3.00) ? "PASSED" : "FAILED";
        return CommandResult.success("Grade " + score + " (" + result + ") added for " + fn + " in " + disciplineName);
    }

    @Override
    public String getName() {
        return "addgrade";
    }

    @Override
    public String getUsage() {
        return "addgrade <fn> \"<discipline>\" <grade>";
    }

    @Override
    public String getDescription() {
        return "Adds a grade for a student";
    }
}
