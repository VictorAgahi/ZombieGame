package fr.epita.zombie.user.domain.models;

import fr.epita.zombie.user.infrastructure.entities.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
  private Long id;
  private String email;
  private String password;
  private Role role;

  // Domain behaviors can be added here
}
