package bg.tu_varna.sit.f24621688.file;

import bg.tu_varna.sit.f24621688.contracts.DataRepository;
import bg.tu_varna.sit.f24621688.enums.CourseType;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;
import bg.tu_varna.sit.f24621688.models.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlStorage {
    private static String currentDirectory = "";

    public static void setCurrentDirectory(String dir) {
        currentDirectory = (dir == null) ? "" : dir;
    }

    public static String getFullPath(String filename) {
        return currentDirectory.isEmpty() ? filename : currentDirectory + "/" + filename;
    }

    public static void saveAllData(DataRepository repo, String filepath) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<university>\n\n");
        sb.append("  <programs>\n");
        for (Program p : repo.getAllPrograms()) {
            sb.append("    <program>\n");
            sb.append("      <name>").append(escape(p.getName())).append("</name>\n");
            sb.append("      <minElectiveCredits>").append(p.getMinElectiveCredits()).append("</minElectiveCredits>\n");
            sb.append("    </program>\n");
        }
        sb.append("  </programs>\n\n");

        sb.append("  <disciplines>\n");
        for (Discipline d : repo.getAllDisciplines()) {
            sb.append("    <discipline>\n");
            sb.append("      <name>").append(escape(d.getName())).append("</name>\n");
            sb.append("      <type>").append(d.getType()).append("</type>\n");
            sb.append("      <credits>").append(d.getCredits()).append("</credits>\n");
            sb.append("      <year>").append(d.getYear()).append("</year>\n");
            sb.append("    </discipline>\n");
        }

        sb.append("  </disciplines>\n\n");
        sb.append("  <students>\n");
        for (Student s : repo.getAllStudents()) {
            sb.append("    <student>\n");
            sb.append("      <name>").append(escape(s.getName())).append("</name>\n");
            sb.append("      <facultyNumber>").append(s.getFacultyNumber()).append("</facultyNumber>\n");
            sb.append("      <course>").append(s.getCourse()).append("</course>\n");
            sb.append("      <program>").append(escape(s.getSpecialty().getName())).append("</program>\n");
            sb.append("      <group>").append(s.getGroup()).append("</group>\n");
            sb.append("      <status>").append(s.getStatus()).append("</status>\n");
            sb.append("      <examRecords>\n");
            for (ExamRecord rec : s.getGrades()) {
                sb.append("        <examRecord>\n");
                sb.append("          <discipline>").append(escape(rec.getDiscipline().getName())).append("</discipline>\n");
                sb.append("          <score>").append(rec.getScore()).append("</score>\n");
                sb.append("        </examRecord>\n");
            }
            sb.append("      </examRecords>\n");
            sb.append("      <registeredCourses>\n");
            for (Discipline d : s.getEnrolledDisciplines()) {
                sb.append("        <discipline>").append(escape(d.getName())).append("</discipline>\n");
            }
            sb.append("      </registeredCourses>\n");
            sb.append("    </student>\n");
        }

        sb.append("  </students>\n\n");
        sb.append("</university>\n");

        writeFile(filepath, sb.toString());
    }

    public static void loadAllData(DataRepository repo, String filepath) throws IOException {
        if (!Files.exists(Paths.get(filepath))) return;

        String xml = readFile(filepath);
        repo.clear();

        String programsSection = extractTag(xml, "programs");
        if (programsSection != null) {
            for (String item : extractTags(programsSection, "program")) {
                String name = extractTag(item, "name");
                String credStr = extractTag(item, "minElectiveCredits");
                int credits = (credStr == null || credStr.isEmpty()) ? 0 : Integer.parseInt(credStr);
                repo.addProgram(new Program(name, credits));
            }
        }

        List<Discipline> loadedDisciplines = new ArrayList<>();
        String disciplinesSection = extractTag(xml, "disciplines");
        if (disciplinesSection != null) {
            for (String item : extractTags(disciplinesSection, "discipline")) {
                String name = extractTag(item, "name");
                CourseType type = CourseType.valueOf(extractTag(item, "type"));
                int credits = Integer.parseInt(extractTag(item, "credits"));
                String yearStr = extractTag(item, "year");
                if (yearStr == null) throw new IOException("Missing <year> in discipline: " + name);
                int year = Integer.parseInt(yearStr);
                if (year < 1 || year > 4) throw new IOException("Year must be 1..4 for: " + name);
                Discipline d = new Discipline(name, type, year);
                d.setCredits(credits);
                repo.addDiscipline(d);
                loadedDisciplines.add(d);
            }
        }

        for (Program p : repo.getAllPrograms()) {
            for (Discipline d : loadedDisciplines) p.addDiscipline(d);
        }

        String studentsSection = extractTag(xml, "students");
        if (studentsSection != null) {
            for (String item : extractTags(studentsSection, "student")) {
                String name       = extractTag(item, "name");
                String fn         = extractTag(item, "facultyNumber");
                int course        = Integer.parseInt(extractTag(item, "course"));
                String progName   = extractTag(item, "program");
                int group         = Integer.parseInt(extractTag(item, "group"));
                StudentStatus st  = StudentStatus.valueOf(extractTag(item, "status"));

                Program program = repo.findProgramByName(progName);
                if (program == null) {
                    program = new Program(progName);
                    repo.addProgram(program);
                    for (Discipline d : loadedDisciplines) program.addDiscipline(d);
                }
                Student student = new Student(name, fn, course, program, group);
                student.setStatus(st);

                String recsSection = extractTag(item, "examRecords");
                if (recsSection != null) {
                    for (String rec : extractTags(recsSection, "examRecord")) {
                        String dName  = extractTag(rec, "discipline");
                        double score  = Double.parseDouble(extractTag(rec, "score"));
                        Discipline d  = repo.findDisciplineByName(dName);
                        if (d != null) student.addGradeDirectly(new ExamRecord(d, score));
                    }
                }

                String regSection = extractTag(item, "registeredCourses");
                if (regSection != null) {
                    for (String dName : extractTags(regSection, "discipline")) {
                        Discipline d = repo.findDisciplineByName(dName);
                        if (d != null) student.addEnrolledDisciplineDirectly(d);
                    }
                }
                repo.addStudent(student);
            }
        }
    }

    private static String readFile(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
    }

    private static void writeFile(String filepath, String content) throws IOException {
        Path path = Paths.get(filepath);
        Path parent = path.getParent();
        if (parent != null && !Files.exists(parent)) Files.createDirectories(parent);
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }

    private static String extractTag(String xml, String tag) {
        String open = "<" + tag + ">";
        String close = "</" + tag + ">";
        int s = xml.indexOf(open);
        if (s == -1) return null;
        s += open.length();
        int e = xml.indexOf(close, s);
        if (e == -1) return null;
        return xml.substring(s, e).trim();
    }

    private static List<String> extractTags(String xml, String tag) {
        List<String> results = new ArrayList<>();
        String open = "<" + tag + ">";
        String close = "</" + tag + ">";
        int idx = 0;
        while (true) {
            int s = xml.indexOf(open, idx);
            if (s == -1) break;
            s += open.length();
            int e = xml.indexOf(close, s);
            if (e == -1) break;
            results.add(xml.substring(s, e).trim());
            idx = e + close.length();
        }
        return results;
    }

    private static String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
