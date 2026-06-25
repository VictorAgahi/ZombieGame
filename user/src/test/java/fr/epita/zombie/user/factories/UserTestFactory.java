package fr.epita.zombie.user.factories;

import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.valueobjects.Role;

public class UserTestFactory {

  private UserTestFactory() {}

  public static UserBuilder aValidUser() {
    return new UserBuilder();
  }

  public static UserBuilder anAdminUser() {
    return new UserBuilder()
        .withId(2L)
        .withEmail("admin@test.com")
        .withPassword("securepass")
        .withRole(Role.ORGANIZER);
  }

  public static class UserBuilder {
    private Long id = 1L;
    private String email = "user@test.com";
    private String password = "password123";
    private Role role = Role.RUNNER;

    public UserBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public UserBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder withRole(Role role) {
      this.role = role;
      return this;
    }

    public UserEntity build() {
      return new UserEntity(id, email, password, role);
    }
  }
}
