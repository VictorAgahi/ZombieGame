package fr.epita.zombie.user.factories;

import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.infrastructure.models.Role;

public class UserTestFactory {

  private UserTestFactory() {}

  public static UserEntity aValidUser() {
    return new UserEntity(1L, "user@test.com", "password123", Role.RUNNER);
  }

  public static UserEntity anAdminUser() {
    return new UserEntity(2L, "admin@test.com", "securepass", Role.ORGANIZER);
  }
}
