package fr.epita.zombie.user.domain.entities;

import fr.epita.zombie.user.infrastructure.models.Role;
import lombok.Getter;

@Getter
public class UserEntity {
  private final Long id;
  private String email;
  private String password;
  private Role role;

  public UserEntity(Long id, String email, String password, Role role) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public void changePassword(String newPassword) {
    this.password = newPassword;
  }

  public void updateProfile(String email, Role role) {
    this.email = email;
    this.role = role;
  }
}
