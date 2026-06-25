package fr.epita.zombie.user.domain.entities;

import fr.epita.zombie.user.domain.valueobjects.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserEntity {
  private final Long id;
  private final String email;
  private final String password;
  private final Role role;
}
