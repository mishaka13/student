package bg.tu_varna.sit.f24621688.exceptions;

/**
 * Thrown when a grade value is outside the valid range of 2.00–6.00.
 */
public class InvalidGradeException extends StudentException {
  /**
   * Constructs an {@code InvalidGradeException} for the given invalid score.
   *
   * @param score the invalid score that triggered the exception
   */
  public InvalidGradeException(double score) {
    super("Invalid grade: " + score + ". Must be between 2.00 and 6.00.");
  }
}
