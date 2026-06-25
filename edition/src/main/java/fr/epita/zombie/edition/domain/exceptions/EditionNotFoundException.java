package fr.epita.zombie.edition.domain.exceptions;

public class EditionNotFoundException extends RuntimeException {
  public EditionNotFoundException(String message) {
    super(message);
  }
}
