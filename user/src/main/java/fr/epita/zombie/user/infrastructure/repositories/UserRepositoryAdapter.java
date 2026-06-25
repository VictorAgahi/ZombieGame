package fr.epita.zombie.user.infrastructure.repositories;

import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.ports.IUserRepository;
import fr.epita.zombie.user.infrastructure.mappers.UserModelMapper;
import fr.epita.zombie.user.infrastructure.models.UserModel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements IUserRepository {

  private final IJpaUserRepository jpaRepository;
  private final UserModelMapper userModelMapper;

  @Override
  public UserEntity save(UserEntity user) {
    UserModel model = userModelMapper.toModel(user);
    UserModel saved = jpaRepository.save(model);
    return userModelMapper.toEntity(saved);
  }

  @Override
  public Optional<UserEntity> findById(Long id) {
    return jpaRepository.findById(id).map(userModelMapper::toEntity);
  }

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    return jpaRepository.findByEmail(email).map(userModelMapper::toEntity);
  }

  @Override
  public boolean existsById(Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public void deleteById(Long id) {
    jpaRepository.deleteById(id);
  }
}
