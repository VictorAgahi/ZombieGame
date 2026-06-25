package fr.epita.zombie.user.domain.ports;

import fr.epita.zombie.user.domain.entities.UserEntity;
import java.util.Optional;

public interface IUserRepository {
  UserEntity save(UserEntity user);

  Optional<UserEntity> findById(Long id);

  Optional<UserEntity> findByEmail(String email);

  boolean existsById(Long id);

  void deleteById(Long id);
}
