package fr.epita.zombie.user.application.mappers;

import fr.epita.zombie.user.application.dtos.requests.UserLoginRequest;
import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.responses.UserLoginResponse;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.domain.models.UserModel;
import fr.epita.zombie.user.infrastructure.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  // DTO -> Domain (Login)
  public UserModel toModel(UserLoginRequest request) {
    if (request == null) {
      return null;
    }
    return UserModel.builder().email(request.email()).password(request.password()).build();
  }

  // DTO -> Domain (Register)
  public UserModel toModel(UserRegisterRequest request) {
    if (request == null) {
      return null;
    }
    return UserModel.builder()
        .email(request.email())
        .password(request.password())
        .role(request.role())
        .build();
  }

  // Domain -> Response DTO
  public UserResponse toResponse(UserModel model) {
    if (model == null) {
      return null;
    }
    return new UserResponse(model.getId(), model.getEmail(), model.getRole());
  }

  // Domain -> Login Response DTO
  public UserLoginResponse toLoginResponse(String token) {
    return new UserLoginResponse(token);
  }

  // Domain -> Entity
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

  // Entity -> Domain
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
}
