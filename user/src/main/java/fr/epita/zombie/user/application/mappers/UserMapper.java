package fr.epita.zombie.user.application.mappers;

import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.requests.UserUpdateRequest;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.domain.entities.UserEntity;
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

  // DTO -> Domain (Update)
  public UserEntity toEntity(UserUpdateRequest request) {
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
}
