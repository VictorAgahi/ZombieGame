package fr.epita.zombie.user.application.mappers;

import fr.epita.zombie.user.application.dtos.requests.UserLoginRequest;
import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.responses.UserLoginResponse;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.infrastructure.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  // DTO -> Domain (Login)
  public UserEntity toEntity(UserLoginRequest request) {
    if (request == null) {
      return null;
    }
    return UserEntity.builder().email(request.email()).password(request.password()).build();
  }

  // DTO -> Domain (Register)
  public UserEntity toEntity(UserRegisterRequest request) {
    if (request == null) {
      return null;
    }
    return UserEntity.builder()
        .email(request.email())
        .password(request.password())
        .role(request.role())
        .build();
  }

  // Domain -> Response DTO
  public UserResponse toResponse(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    return new UserResponse(entity.getId(), entity.getEmail(), entity.getRole());
  }

  // Domain -> Login Response DTO
  public UserLoginResponse toLoginResponse(String token) {
    return new UserLoginResponse(token);
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
    return UserEntity.builder()
        .id(model.getId())
        .email(model.getEmail())
        .password(model.getPassword())
        .role(model.getRole())
        .build();
  }
}
