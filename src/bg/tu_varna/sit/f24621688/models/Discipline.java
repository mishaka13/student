package bg.tu_varna.sit.f24621688.models;

import bg.tu_varna.sit.f24621688.enums.DisciplineType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an academic discipline (course) that can be offered in one or more years for a specific program.
 * Each discipline has a name, type (mandatory/elective), the list of years it can be taken in, and credits (for electives).
 */
public class Discipline implements Serializable {
    /** Name of the discipline. */
    private String name;
    /** Whether the discipline is mandatory or elective. */
    private DisciplineType type;
    /** The years in which this discipline can be enrolled (e.g. [2, 3] means year 2 and 3). */
    private List<Integer> years;
    /** Credit count (relevant for elective disciplines). */
    private int credits;

    /**
     * Constructs a Discipline with a single year and a credit count.
     * @param name     the name of the discipline.
     * @param type     mandatory or elective.
     * @param year     the year the discipline belongs to.
     * @param credits  the number of credits (used for electives).
     */
    public Discipline(String name, DisciplineType type, int year, int credits) {
        this.name = name;
        this.type = type;
        this.years = new ArrayList<>();
        this.years.add(year);
        this.credits = credits;
    }

    public String getName() { return name; }
    public DisciplineType getType() { return type; }
    public List<Integer> getYears() { return years; }
    public int getCredits() { return credits; }

    /**
     * Checks whether this discipline can be enrolled for a specific year.
     * @param year the year to check.
     * @return true if the year is in the discipline's year list.
     */
    public boolean isOfferedInYear(int year) {
        return years.contains(year);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Discipline d)) return false;
        return Objects.equals(name, d.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Discipline{name='" + name + "', type=" + type + ", years=" + years + ", credits=" + credits + "}";
    }
}
