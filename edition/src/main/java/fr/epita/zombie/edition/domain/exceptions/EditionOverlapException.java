package fr.epita.zombie.edition.domain.exceptions;

public class EditionOverlapException extends RuntimeException {
  public EditionOverlapException(String message) {
    super(message);
  }
}
