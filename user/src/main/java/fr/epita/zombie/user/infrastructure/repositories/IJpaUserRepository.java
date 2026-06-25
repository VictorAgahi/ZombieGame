package fr.epita.zombie.user.infrastructure.repositories;

import fr.epita.zombie.user.infrastructure.models.UserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJpaUserRepository extends JpaRepository<UserModel, Long> {
  Optional<UserModel> findByEmail(String email);
}
