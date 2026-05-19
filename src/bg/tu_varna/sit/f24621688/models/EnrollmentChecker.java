package bg.tu_varna.sit.f24621688.models;

import bg.tu_varna.sit.f24621688.enums.StudentStatus;

/**
 * Utility class that validates whether a student may enroll in a given discipline.
 * All methods are static; this class is not meant to be instantiated.
 */
public class EnrollmentChecker {
    /**
     * Returns whether the student is allowed to enroll in the given discipline.
     *
     * @param student    the student requesting enrollment
     * @param discipline the discipline to enroll in
     * @return {@code true} if all conditions are met
     */
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
