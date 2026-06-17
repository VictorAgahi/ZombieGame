package fr.epita.zombie.user.infrastructure.repositories;

import fr.epita.zombie.user.infrastructure.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);
}
