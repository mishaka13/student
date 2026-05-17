package bg.tu_varna.sit.f24621688.models;

import bg.tu_varna.sit.f24621688.enums.StudentStatus;

public class EnrollmentChecker {
    public static boolean canEnroll(Student student, Discipline discipline) {
        if (student.getStatus() != StudentStatus.ENROLLED)
            return false;

        if (discipline.getYear() != student.getCourse())
            return false;
        if (!student.getSpecialty().getDisciplines().contains(discipline))
            return false;
        if (student.getEnrolledDisciplines().contains(discipline))
            return false;
        for (ExamRecord rec : student.getGrades()) {
            if (rec.getDiscipline().equals(discipline))
                return false;
        }

        return true;
    }
}
