package fr.epita.zombie.user.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found");
  }

  public UserNotFoundException(Long id) {
    super("User " + id + " not found");
  }

  public UserNotFoundException(String message) {
    super(message);
  }
}
