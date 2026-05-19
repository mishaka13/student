package bg.tu_varna.sit.f24621688.commands.grade;

import bg.tu_varna.sit.f24621688.commands.BaseCommand;
import bg.tu_varna.sit.f24621688.commands.CommandResult;
import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.models.ExamRecord;
import bg.tu_varna.sit.f24621688.models.Student;
import bg.tu_varna.sit.f24621688.session.AppSession;

import java.util.List;

/**
 * Prints an exam protocol for all students registered in a given discipline.
 * Students are grouped by program and year, and sorted by faculty number.
 */
public class ProtocolCommand extends BaseCommand {

    public ProtocolCommand(AppSession session) { super(session); }

    /**
     * @param args the command arguments; discipline name follows the keyword
     * @return a formatted protocol or an error if the discipline is not found
     */
    @Override
    public CommandResult execute(String[] args) {
        if (!getSession().isFileOpen()) return CommandResult.error("No file is open.");
        if (args.length < 2) return CommandResult.error("Usage: protocol \"<discipline>\"");

        StringBuilder db = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) db.append(" ");
            db.append(args[i]);
        }
        String disciplineName = db.toString().replace("\"", "").trim();

        Discipline discipline = getRepository().findDisciplineByName(disciplineName);
        if (discipline == null) return CommandResult.error("Discipline '" + disciplineName + "' not found.");

        List<Student> students = getRepository().getStudentsForProtocol(discipline);
        if (students.isEmpty()) return CommandResult.success("No students enrolled in " + disciplineName);

        StringBuilder sb = new StringBuilder("\nProtocol: " + disciplineName + "\n");
        sb.append("========================================\n");

        String lastSpecialty = "";
        int lastYear = -1;

        for (Student s : students) {
            String spec = s.getSpecialty().getName();
            int year    = s.getCourse();

            if (!spec.equals(lastSpecialty) || year != lastYear) {
                lastSpecialty = spec;
                lastYear = year;
                sb.append("\n--- ").append(spec).append(" - Year ").append(year).append(" ---\n");
                sb.append("FN\t\tName\t\t\tGroup\tScore\tResult\n");
                sb.append("--------------------------------------------------\n");
            }
            ExamRecord rec = null;
            for (ExamRecord r : s.getGrades()) {
                if (r.getDiscipline().equals(discipline)) { rec = r; break; }
            }

            String scoreStr = (rec == null) ? "pending" : String.format("%.2f", rec.getScore());
            String result   = (rec == null) ? "PENDING"
                    : (rec.isPassed() ? "PASSED" : "FAILED");

            sb.append(s.getFacultyNumber()).append("\t")
                    .append(s.getName()).append("\t\t")
                    .append("Group ").append(s.getGroup()).append("\t")
                    .append(scoreStr).append("\t").append(result).append("\n");
        }

        return CommandResult.success(sb.toString());
    }

    @Override
    public String getName() {
        return "protocol";
    }

    @Override
    public String getUsage() {
        return "protocol \"<discipline>\"";
    }

    @Override
    public String getDescription() {
        return "Shows exam protocol for a discipline";
    }
}
