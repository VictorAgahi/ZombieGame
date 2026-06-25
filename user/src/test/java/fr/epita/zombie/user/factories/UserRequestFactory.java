package fr.epita.zombie.user.factories;

import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.requests.UserUpdateRequest;
import fr.epita.zombie.user.domain.valueobjects.Role;

public class UserRequestFactory {

  private UserRequestFactory() {}

  public static UserRegisterRequest aValidRegisterRequest() {
    return new UserRegisterRequest("newuser@test.com", "password123", Role.RUNNER);
  }

  public static UserUpdateRequest aValidUpdateRequest() {
    return new UserUpdateRequest("updated@test.com", "newpassword123", Role.RUNNER);
  }
}
