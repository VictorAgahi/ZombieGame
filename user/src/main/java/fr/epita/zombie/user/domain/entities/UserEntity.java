package fr.epita.zombie.user.domain.entities;

import fr.epita.zombie.user.infrastructure.models.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
  private Long id;
  private String email;
  private String password;
  private Role role;

  // Domain behaviors can be added here
}
