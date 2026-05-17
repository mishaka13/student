package bg.tu_varna.sit.f24621688.models;

import bg.tu_varna.sit.f24621688.enums.CourseType;
import bg.tu_varna.sit.f24621688.enums.StudentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private final String fullName;
    private final String facultyNumber;
    private int currentYear;
    private Program program;
    private int groupNumber;
    private StudentStatus status;
    private final List<ExamRecord> examRecords;
    private final List<Discipline> registeredCourses;
    private double gpa;

    public Student(String fullName, String facultyNumber, int currentYear, Program program, int groupNumber) {
        this.fullName = fullName;
        this.facultyNumber = facultyNumber;
        this.currentYear = currentYear;
        this.program = program;
        this.groupNumber = groupNumber;
        this.status = StudentStatus.ENROLLED;
        this.examRecords = new ArrayList<>();
        this.registeredCourses = new ArrayList<>();
        this.gpa = 0.0;
        recalculateGpa();
    }

    public String getName() {
        return fullName;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public int getCourse() {
        return currentYear;
    }

    public Program getSpecialty() {
        return program;
    }

    public int getGroup() {
        return groupNumber;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public List<ExamRecord> getGrades() {
        return examRecords;
    }

    public List<Discipline> getEnrolledDisciplines() {
        return registeredCourses;
    }

    public double getAverageGrade() {
        return gpa;
    }

    public void setCourse(int year) {
        this.currentYear = year;
    }

    public void setSpecialty(Program program) {
        this.program = program;
    }

    public void setGroup(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public boolean hasGradeForDiscipline(Discipline discipline) {
        for (ExamRecord rec : examRecords) {
            if (rec.getDiscipline().equals(discipline)) return true;
        }
        return false;
    }

    public ExamRecord getGradeForDiscipline(Discipline discipline) {
        for (ExamRecord rec : examRecords) {
            if (rec.getDiscipline().equals(discipline)) return rec;
        }
        return null;
    }

    private boolean hasPassedDiscipline(Discipline discipline) {
        ExamRecord rec = getGradeForDiscipline(discipline);
        return rec != null && rec.isPassed();
    }

    private void recalculateGpa() {
        double sum = 0.0;
        int count = 0;

        for (ExamRecord rec : examRecords) {
            sum += rec.getScore();
            count++;
        }

        for (Discipline d : registeredCourses) {
            if (!hasGradeForDiscipline(d)) {
                sum += 2.00;
                count++;
            }
        }

        gpa = (count == 0) ? 0.0 : sum / count;
    }

    public boolean addGrade(ExamRecord record) {
        if (status != StudentStatus.ENROLLED) return false;
        if (!registeredCourses.contains(record.getDiscipline())) return false;
        examRecords.add(record);
        recalculateGpa();
        return true;
    }

    public void addGradeDirectly(ExamRecord record) {
        examRecords.add(record);
        recalculateGpa();
    }

    public void addEnrolledDisciplineDirectly(Discipline discipline) {
        if (!registeredCourses.contains(discipline)) {
            registeredCourses.add(discipline);
            recalculateGpa();
        }
    }

    public boolean enrollInDiscipline(Discipline discipline) {
        if (!EnrollmentChecker.canEnroll(this, discipline)) return false;
        if (!registeredCourses.contains(discipline)) {
            registeredCourses.add(discipline);
            recalculateGpa();
        }
        return true;
    }

    public boolean canAdvance() {
        int failedMandatory = 0;
        for (Discipline d : program.getDisciplines()) {
            if (d.getType() == CourseType.MANDATORY && d.getYear() <= currentYear) {
                if (!hasPassedDiscipline(d)) failedMandatory++;
            }
        }
        return failedMandatory <= 2;
    }

    public boolean canGraduate() {
        for (Discipline d : registeredCourses) {
            if (!hasPassedDiscipline(d)) return false;
        }
        return getRemainingElectiveCredits() == 0;
    }


    public int getEarnedElectiveCredits() {
        int credits = 0;
        for (ExamRecord rec : examRecords) {
            Discipline d = rec.getDiscipline();
            if (d.getType() == CourseType.ELECTIVE && rec.isPassed()) {
                credits += d.getCredits();
            }
        }
        return credits;
    }

    public int getRemainingElectiveCredits() {
        return Math.max(program.getMinElectiveCredits() - getEarnedElectiveCredits(), 0);
    }

    public List<ExamRecord> getPassedExams() {
        List<ExamRecord> result = new ArrayList<>();
        for (ExamRecord rec : examRecords) {
            if (rec.isPassed()) result.add(rec);
        }
        return result;
    }

    public List<Discipline> getFailedExams() {
        List<Discipline> result = new ArrayList<>();
        for (Discipline d : registeredCourses) {
            if (!hasPassedDiscipline(d)) result.add(d);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student s = (Student) o;
        return Objects.equals(facultyNumber, s.facultyNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facultyNumber);
    }
}
