package fr.epita.zombie.user.application.mappers;

import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.infrastructure.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  // DTO -> Domain (Register)
  public UserEntity toEntity(UserRegisterRequest request) {
    if (request == null) {
      return null;
    }
    return new UserEntity(null, request.email(), request.password(), request.role());
  }

  // Domain -> Response DTO
  public UserResponse toResponse(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    return new UserResponse(entity.getId(), entity.getEmail(), entity.getRole());
  }

  // Domain -> DB Model
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

  // DB Model -> Domain
  public UserEntity toEntity(UserModel model) {
    if (model == null) {
      return null;
    }
    return new UserEntity(model.getId(), model.getEmail(), model.getPassword(), model.getRole());
  }
}
