package bg.tu_varna.sit.f24621688.models;

import bg.tu_varna.sit.f24621688.enums.StudentStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a student in the information system.
 * <p>
 * Each student stores: name, faculty number (fn), program, group, year, status,
 * enrolled disciplines (with optional grades), and average GPA.
 */
public class Student implements Serializable {
    /** Full name of the student. */
    private String name;
    /** Unique faculty number. */
    private String fn;
    /** The academic program (specialty) the student is enrolled in. */
    private String program;
    /** Student's group number. */
    private String group;
    /** Current year of study. */
    private int year;
    /** Current enrollment status. */
    private StudentStatus status;

    /**
     * Disciplines the student has enrolled in, mapped to their grade.
     * A {@code null} grade means the student has enrolled but not yet sat the exam.
     */
    private Map<Discipline, Double> grades;

    /**
     * Constructs a new Student enrolling in year 1.
     * @param fn      faculty number.
     * @param program academic program.
     * @param group   student group.
     * @param name    full name.
     */
    public Student(String fn, String program, String group, String name) {
        this.fn = fn;
        this.program = program;
        this.group = group;
        this.name = name;
        this.year = 1;
        this.status = StudentStatus.ENROLLED;
        this.grades = new HashMap<>();
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getName()    { return name; }
    public String getFn()      { return fn; }
    public String getProgram() { return program; }
    public String getGroup()   { return group; }
    public int    getYear()    { return year; }
    public StudentStatus getStatus() { return status; }
    public Map<Discipline, Double> getGrades() { return grades; }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setProgram(String program) { this.program = program; }
    public void setGroup(String group)     { this.group = group; }
    public void setYear(int year)          { this.year = year; }
    public void setStatus(StudentStatus status) { this.status = status; }

    // ── Domain helpers ───────────────────────────────────────────────────────

    /**
     * Returns the average GPA calculated from disciplines that have a grade.
     * Disciplines without a grade (value {@code null}) are counted as 2.0 (fail).
     * @return the average grade, or 0.0 if no disciplines are enrolled.
     */
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;
        double sum = 0;
        for (Map.Entry<Discipline, Double> e : grades.entrySet()) {
            sum += (e.getValue() == null ? 2.0 : e.getValue());
        }
        return sum / grades.size();
    }

    /**
     * Returns only disciplines for which a grade exists (exam passed or failed).
     * @return list of disciplines with recorded grades.
     */
    public List<Discipline> getDisciplinesWithGrades() {
        List<Discipline> result = new ArrayList<>();
        for (Map.Entry<Discipline, Double> e : grades.entrySet()) {
            if (e.getValue() != null) result.add(e.getKey());
        }
        return result;
    }

    /**
     * Returns only disciplines that have been enrolled but have no grade yet.
     * @return list of disciplines without grades.
     */
    public List<Discipline> getDisciplinesWithoutGrades() {
        List<Discipline> result = new ArrayList<>();
        for (Map.Entry<Discipline, Double> e : grades.entrySet()) {
            if (e.getValue() == null) result.add(e.getKey());
        }
        return result;
    }

    /**
     * Checks whether the student has passed all mandatory disciplines
     * from all years strictly before {@code year} (allowing up to {@code maxFailed} skipped years).
     * Used for advancing to the next year.
     * @param year       the year boundary (exclusive).
     * @param maxFailed  the maximum number of past years with at least one missed mandatory discipline.
     * @return true if the advance is allowed.
     */
    public boolean canAdvance(int year, int maxFailed) {
        // Group mandatory disciplines by the year they belong to
        Map<Integer, List<Discipline>> mandatoryByYear = new HashMap<>();
        for (Discipline d : grades.keySet()) {
            if (d.getType().toString().equals("mandatory")) {
                for (int y : d.getYears()) {
                    if (y < year) {
                        mandatoryByYear.computeIfAbsent(y, k -> new ArrayList<>()).add(d);
                    }
                }
            }
        }

        int failedYears = 0;
        for (Map.Entry<Integer, List<Discipline>> entry : mandatoryByYear.entrySet()) {
            for (Discipline d : entry.getValue()) {
                Double grade = grades.get(d);
                if (grade == null || grade < 3.0) {
                    failedYears++;
                    break; // one miss per year counts once
                }
            }
        }
        return failedYears <= maxFailed;
    }

    /**
     * Checks whether the student has passed all mandatory disciplines from past years
     * in the specified program (used for program transfers).
     * @param mandatory list of mandatory disciplines of the target program.
     * @return true if all mandatory disciplines of past years have a passing grade.
     */
    public boolean hasPassedMandatoryForProgram(List<Discipline> mandatory) {
        for (Discipline d : mandatory) {
            for (int y : d.getYears()) {
                if (y < this.year) {
                    Double grade = grades.get(d);
                    if (grade == null || grade < 3.0) return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student s)) return false;
        return Objects.equals(fn, s.fn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fn);
    }

    @Override
    public String toString() {
        return "Student{fn='" + fn + "', name='" + name + "', program='" + program
                + "', group='" + group + "', year=" + year + ", status=" + status + "}";
    }
}
