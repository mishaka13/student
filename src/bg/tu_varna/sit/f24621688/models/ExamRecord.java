package bg.tu_varna.sit.f24621688.models;

public class ExamRecord {
    private final Discipline discipline;
    private final double score;

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

    public boolean isPassed() {
        return score >= 3.00;
    }
}
