package bg.tu_varna.sit.f24621688.models;

import bg.tu_varna.sit.f24621688.contracts.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class AcademicRegistry implements DataRepository {
    private final List<Student> students;
    private final List<Program> programs;
    private final List<Discipline> disciplines;

    public AcademicRegistry() {
        this.students = new ArrayList<>();
        this.programs = new ArrayList<>();
        this.disciplines = new ArrayList<>();
    }

    @Override
    public void addStudent(Student student) {
        if (!students.contains(student)) students.add(student);
    }

    @Override
    public Student findStudentByFacultyNumber(String fn) {
        for (Student s : students) {
            if (s.getFacultyNumber().equals(fn)) return s;
        }
        return null;
    }
    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public List<Student> getStudentsForProtocol(Discipline discipline) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getEnrolledDisciplines().contains(discipline)) result.add(s);
        }

        for (int i = 0; i < result.size() - 1; i++) {
            for (int j = 0; j < result.size() - i - 1; j++) {
                if (result.get(j).getFacultyNumber()
                        .compareTo(result.get(j + 1).getFacultyNumber()) > 0) {
                    Student tmp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, tmp);
                }
            }
        }
        return result;
    }

    @Override
    public void addProgram(Program program) {
        if (!programs.contains(program)) programs.add(program);
    }

    @Override
    public void removeProgram(Program program) {
        programs.remove(program);
    }

    @Override
    public Program findProgramByName(String name) {
        for (Program p : programs) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    @Override
    public List<Program> getAllPrograms() {
        return new ArrayList<>(programs);
    }


    @Override
    public void addDiscipline(Discipline discipline) {
        if (!disciplines.contains(discipline)) disciplines.add(discipline);
    }

    @Override
    public void removeDiscipline(Discipline discipline) {
        disciplines.remove(discipline);
    }

    @Override
    public Discipline findDisciplineByName(String name) {
        for (Discipline d : disciplines) {
            if (d.getName().equalsIgnoreCase(name)) return d;
        }
        return null;
    }

    @Override
    public List<Discipline> getAllDisciplines() {
        return new ArrayList<>(disciplines);
    }

    @Override
    public void clear() {
        students.clear();
        programs.clear();
        disciplines.clear();
    }

    @Override
    public boolean isEmpty() {
        return students.isEmpty() && programs.isEmpty() && disciplines.isEmpty();
    }
}
