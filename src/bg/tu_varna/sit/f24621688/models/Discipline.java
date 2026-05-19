package bg.tu_varna.sit.f24621688.models;

import bg.tu_varna.sit.f24621688.enums.CourseType;

import java.util.Objects;

/**
 * an academic discipline (course) in the system.
 */
public class Discipline {
    private final String name;
    private final CourseType type;
    private int credits;
    private final int year;

    /**
     * Constructs a {@code Discipline} with the given name, type, and year.
     * Credits default to {@code 0}.
     *
     * @param name the unique discipline name
     * @param type {@link CourseType#MANDATORY} or {@link CourseType#ELECTIVE}
     * @param year the academic year in which this discipline is offered (1–4)
     */
    public Discipline(String name, CourseType type, int year) {
        this.name = name;
        this.type = type;
        this.credits = 0;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public CourseType getType() {
        return type;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (type == CourseType.ELECTIVE) {
            this.credits = credits;
        }
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
