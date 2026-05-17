package bg.tu_varna.sit.f24621688.contracts;

import bg.tu_varna.sit.f24621688.models.Discipline;
import bg.tu_varna.sit.f24621688.models.Program;   // Преименуван от Specialty
import bg.tu_varna.sit.f24621688.models.Student;

import java.util.List;

public interface DataRepository {

    void addStudent(Student student);
    Student findStudentByFacultyNumber(String fn);
    List<Student> getAllStudents();
    List<Student> getStudentsForProtocol(Discipline discipline);

    void addProgram(Program program);
    void removeProgram(Program program);
    Program findProgramByName(String name);
    List<Program> getAllPrograms();

    void addDiscipline(Discipline discipline);
    void removeDiscipline(Discipline discipline);
    Discipline findDisciplineByName(String name);
    List<Discipline> getAllDisciplines();

    void clear();
    boolean isEmpty();
}
