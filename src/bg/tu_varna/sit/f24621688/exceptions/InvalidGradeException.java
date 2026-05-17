package bg.tu_varna.sit.f24621688.exceptions;

public class InvalidGradeException extends StudentException {
  public InvalidGradeException(double score) {
    super("Invalid grade: " + score + ". Must be between 2.00 and 6.00.");
  }
}
