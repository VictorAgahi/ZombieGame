package fr.epita.zombie.edition.domain.exceptions;

public class InvalidEditionTimeException extends RuntimeException {
  public InvalidEditionTimeException(String message) {
    super(message);
  }
}
