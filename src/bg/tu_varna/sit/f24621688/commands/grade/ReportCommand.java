package bg.tu_varna.sit.f24621688.commands.grade;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.models.ExamRecord;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.util.List;

public class ReportCommand extends BaseCommand {

    public ReportCommand(AppSession session) { super(session); }

    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: report <fn>");

        String fn = args[1];
        Student s = getRepository().findStudentByFacultyNumber(fn);
        if (s == null) return CommandResult.error("Student " + fn + " not found.");

        StringBuilder sb = new StringBuilder();
        sb.append("\nAcademic Report: ").append(s.getName()).append("\n");
        sb.append("Faculty Number:  ").append(s.getFacultyNumber()).append("\n");
        sb.append("Specialty:       ").append(s.getSpecialty().getName()).append("\n\n");

        sb.append("PASSED EXAMS:\n-------------\n");
        List<ExamRecord> passed = s.getPassedExams();
        if (passed.isEmpty()) {
            sb.append("None.\n");
        } else {
            for (ExamRecord r : passed) {
                sb.append("  ").append(r.getDiscipline().getName())
                        .append(": ").append(String.format("%.2f", r.getScore())).append("\n");
            }
        }

        sb.append("\nFAILED / PENDING EXAMS:\n------------------------\n");
        List<Discipline> failed = s.getFailedExams();
        if (failed.isEmpty()) {
            sb.append("None.\n");
        } else {
            for (Discipline d : failed) {
                sb.append("  ").append(d.getName()).append("\n");
            }
        }

        sb.append("\nGPA:                     ").append(String.format("%.2f", s.getAverageGrade())).append("\n");
        sb.append("Earned Elective Credits: ").append(s.getEarnedElectiveCredits()).append("\n");
        sb.append("Remaining Credits:       ").append(s.getRemainingElectiveCredits()).append("\n");

        return CommandResult.success(sb.toString());
    }

    @Override
    public String getName() {
        return "report";
    }

    @Override
    public String getUsage() {
        return "report <fn>";
    }

    @Override
    public String getDescription() {
        return "Shows academic report for a student";
    }
}
