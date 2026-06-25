package fr.epita.zombie.user.infrastructure.mappers;

import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.infrastructure.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

  public UserModel toModel(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    return UserModel.builder()
        .id(entity.getId())
        .email(entity.getEmail())
        .password(entity.getPassword())
        .role(entity.getRole())
        .build();
  }

  public UserEntity toEntity(UserModel model) {
    if (model == null) {
      return null;
    }
    return new UserEntity(model.getId(), model.getEmail(), model.getPassword(), model.getRole());
  }
}
