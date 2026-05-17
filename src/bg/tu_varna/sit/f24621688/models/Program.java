package bg.tu_varna.sit.f24621688.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Program {
    private final String name;
    private final List<Discipline> disciplines;
    private final int minElectiveCredits;

    public Program(String name) {
        this.name = name;
        this.disciplines = new ArrayList<>();
        this.minElectiveCredits = 0;
    }

    public Program(String name, int minElectiveCredits) {
        this.name = name;
        this.disciplines = new ArrayList<>();
        this.minElectiveCredits = minElectiveCredits;
    }

    public String getName() {
        return name; }

    public List<Discipline> getDisciplines() {
        return disciplines; }

    public int getMinElectiveCredits() {
        return minElectiveCredits; }

    public void addDiscipline(Discipline discipline) {
        if (discipline == null) return;
        if (!disciplines.contains(discipline)) {
            disciplines.add(discipline);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program that = (Program) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
