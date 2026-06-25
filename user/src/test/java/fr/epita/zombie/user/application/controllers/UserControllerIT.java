package fr.epita.zombie.user.application.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.requests.UserUpdateRequest;
import fr.epita.zombie.user.application.mappers.UserMapper;
import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
import fr.epita.zombie.user.domain.services.UserService;
import fr.epita.zombie.user.factories.UserRequestFactory;
import fr.epita.zombie.user.factories.UserTestFactory;
import fr.epita.zombie.user.infrastructure.models.Role;
import fr.epita.zombie.user.infrastructure.models.UserModel;
import fr.epita.zombie.user.infrastructure.security.UserDetailsConnected;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UserMapper.class)
class UserControllerIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private UserService userService;

  private UserDetailsConnected createMockUser() {
    UserModel model =
        UserModel.builder()
            .id(1L)
            .email("user@test.com")
            .password("password")
            .role(Role.RUNNER)
            .build();
    return new UserDetailsConnected(model);
  }

  @Test
  void should_return_201_when_registration_is_successful() throws Exception {
    // Arrange
    UserRegisterRequest request = UserRequestFactory.aValidRegisterRequest();
    UserEntity registeredUser = UserTestFactory.aValidUser();
    when(userService.register(any(UserEntity.class))).thenReturn(registeredUser);

    // Act & Assert
    mockMvc
        .perform(
            post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(registeredUser.getId()))
        .andExpect(jsonPath("$.email").value(registeredUser.getEmail()));

    verify(userService).register(any(UserEntity.class));
  }

  @Test
  void should_return_400_when_registration_fails_due_to_existing_user() throws Exception {
    // Arrange
    UserRegisterRequest request = UserRequestFactory.aValidRegisterRequest();
    when(userService.register(any(UserEntity.class)))
        .thenThrow(new UserAlreadyExistsException("Already exists"));

    // Act & Assert
    mockMvc
        .perform(
            post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Registration failed: invalid request"));
  }

  @Test
  void should_return_400_when_registration_request_is_invalid() throws Exception {
    // Arrange
    UserRegisterRequest invalidRequest = new UserRegisterRequest("", "short", null); // Invalid data

    // Act & Assert
    mockMvc
        .perform(
            post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void should_return_200_when_get_me_is_successful() throws Exception {
    // Arrange
    UserDetailsConnected mockUser = createMockUser();
    UserEntity userEntity = UserTestFactory.aValidUser();
    when(userService.getById(mockUser.getId())).thenReturn(userEntity);

    // Act & Assert
    mockMvc
        .perform(get("/api/users/me").with(user(mockUser)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(userEntity.getId()))
        .andExpect(jsonPath("$.email").value(userEntity.getEmail()));
  }

  @Test
  void should_return_404_when_get_me_user_not_found() throws Exception {
    // Arrange
    UserDetailsConnected mockUser = createMockUser();
    when(userService.getById(mockUser.getId())).thenThrow(new UserNotFoundException("Not found"));

    // Act & Assert
    mockMvc
        .perform(get("/api/users/me").with(user(mockUser)))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value("Not found"));
  }

  @Test
  void should_return_200_when_update_me_is_successful() throws Exception {
    // Arrange
    UserDetailsConnected mockUser = createMockUser();
    UserUpdateRequest request = UserRequestFactory.aValidUpdateRequest();
    UserEntity updatedUser = UserTestFactory.aValidUser();
    updatedUser.updateProfile("updated@test.com", updatedUser.getRole());
    when(userService.update(eq(mockUser.getId()), any(UserEntity.class))).thenReturn(updatedUser);

    // Act & Assert
    mockMvc
        .perform(
            put("/api/users/me")
                .with(user(mockUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("updated@test.com"));
  }

  @Test
  void should_return_204_when_delete_me_is_successful() throws Exception {
    // Arrange
    UserDetailsConnected mockUser = createMockUser();

    // Act & Assert
    mockMvc.perform(delete("/api/users/me").with(user(mockUser))).andExpect(status().isNoContent());

    verify(userService).delete(mockUser.getId());
  }
}
