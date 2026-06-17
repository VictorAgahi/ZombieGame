package fr.epita.zombie.user.application.mappers;

import fr.epita.zombie.user.application.dtos.requests.UserRequest;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.infrastructure.entities.Role;
import fr.epita.zombie.user.infrastructure.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserEntity toEntity(UserRequest request) {
    if (request == null) {
      return null;
    }
    return UserEntity.builder()
        .email(request.email())
        .password(request.password())
        // Default role or role from request if it existed, but here we'll assume a default or handle it in service
        .role(Role.COUREUR) 
        .build();
  }

  public UserResponse toResponse(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    return new UserResponse(entity.getEmail(), entity.getPassword());
  }
}
