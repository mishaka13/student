package bg.tu_varna.sit.f24621688.models;

/**
 * Represents the result of an exam taken by a student.
 */
public class ExamRecord {
    private final Discipline discipline;
    private final double score;

    /**
     * Constructs an {@code ExamRecord} for the given discipline and score.
     *
     * @param discipline the discipline examined
     * @param score      the numeric score (2.00–6.00)
     */
    public ExamRecord(Discipline discipline, double score) {
        this.discipline = discipline;
        this.score = score;
    }
    public Discipline getDiscipline() {
        return discipline;
    }

    public double getScore() {
        return score;
    }

    /**
     * Returns whether the exam was passed.
     * An exam is passed if the score is at least 3.00.
     *
     * @return {@code true} if score &ge; 3.00
     */
    public boolean isPassed() {
        return score >= 3.00;
    }
}
