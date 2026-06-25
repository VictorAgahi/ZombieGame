package fr.epita.zombie.edition.domain.exceptions;

public class InvalidEditionDateException extends RuntimeException {
  public InvalidEditionDateException(String message) {
    super(message);
  }
}
