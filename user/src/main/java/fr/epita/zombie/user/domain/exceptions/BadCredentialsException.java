package fr.epita.zombie.user.domain.exceptions;

public class BadCredentialsException extends RuntimeException {
  public BadCredentialsException(String message) {
    super(message);
  }
}
