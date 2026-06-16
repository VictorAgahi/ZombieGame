package fr.epita.zombie.user.infrastructure.repositories;

import fr.epita.zombie.user.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  // TODO: Define custom queries if necessary
}
