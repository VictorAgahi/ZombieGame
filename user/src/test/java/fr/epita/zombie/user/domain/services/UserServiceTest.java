package fr.epita.zombie.user.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
import fr.epita.zombie.user.domain.ports.IUserRepository;
import fr.epita.zombie.user.factories.UserTestFactory;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private IEncryptionService encryptionService;
  @Mock private IUserRepository userRepository;

  @InjectMocks private UserService userService;

  @Test
  void should_register_user_successfully() {
    // Arrange
    UserEntity userToRegister = UserTestFactory.aValidUser().build();
    when(userRepository.findByEmail(userToRegister.getEmail())).thenReturn(Optional.empty());
    when(encryptionService.encrypt("password123")).thenReturn("hashedPassword");
    when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);

    // Act
    UserEntity result = userService.register(userToRegister);

    // Assert
    assertThat(result.getPassword()).isEqualTo("hashedPassword");
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void should_throw_exception_when_registering_existing_user() {
    // Arrange
    UserEntity userToRegister = UserTestFactory.aValidUser().build();
    when(userRepository.findByEmail(userToRegister.getEmail()))
        .thenReturn(Optional.of(userToRegister));

    // Act & Assert
    assertThrows(UserAlreadyExistsException.class, () -> userService.register(userToRegister));
    verify(userRepository, never()).save(any());
  }

  @Test
  void should_get_user_by_id_successfully() {
    // Arrange
    UserEntity user = UserTestFactory.aValidUser().build();
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    // Act
    UserEntity result = userService.getById(1L);

    // Assert
    assertThat(result).isEqualTo(user);
  }

  @Test
  void should_throw_exception_when_getting_non_existent_user() {
    // Arrange
    when(userRepository.findById(99L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> userService.getById(99L));
  }

  @Test
  void should_update_user_profile_and_password_successfully() {
    // Arrange
    UserEntity existingUser = UserTestFactory.aValidUser().build();
    UserEntity updateRequest =
        UserTestFactory.aValidUser().withEmail("new@test.com").withPassword("newpass").build();

    when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
    when(encryptionService.encrypt("newpass")).thenReturn("newHashedPass");
    when(userRepository.save(any(UserEntity.class))).thenAnswer(i -> i.getArguments()[0]);

    // Act
    UserEntity result = userService.update(1L, updateRequest);

    // Assert
    assertThat(result.getEmail()).isEqualTo("new@test.com");
    assertThat(result.getPassword()).isEqualTo("newHashedPass");
    verify(userRepository).save(any(UserEntity.class));
  }

  @Test
  void should_delete_user_successfully() {
    // Arrange
    when(userRepository.existsById(1L)).thenReturn(true);

    // Act
    userService.delete(1L);

    // Assert
    verify(userRepository).deleteById(1L);
  }

  @Test
  void should_throw_exception_when_deleting_non_existent_user() {
    // Arrange
    when(userRepository.existsById(99L)).thenReturn(false);

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> userService.delete(99L));
    verify(userRepository, never()).deleteById(any());
  }
}
