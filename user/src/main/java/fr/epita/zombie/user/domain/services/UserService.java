package fr.epita.zombie.user.domain.services;

import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
import fr.epita.zombie.user.domain.ports.IUserRepository;

public class UserService {
  private final IEncryptionService encryptionService;
  private final IUserRepository userRepository;

  public UserService(IEncryptionService encryptionService, IUserRepository userRepository) {
    this.encryptionService = encryptionService;
    this.userRepository = userRepository;
  }

  public UserEntity register(UserEntity user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists");
    }

    var updatedUser =
        user.toBuilder().password(encryptionService.encrypt(user.getPassword())).build();
    return userRepository.save(updatedUser);
  }

  public UserEntity getById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  public UserEntity update(Long id, UserEntity user) {
    UserEntity existingUser =
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    var builder = existingUser.toBuilder();

    if (user.getEmail() != null && !user.getEmail().isBlank()) {
      builder.email(user.getEmail());
    }
    if (user.getRole() != null) {
      builder.role(user.getRole());
    }
    if (user.getPassword() != null && !user.getPassword().isBlank()) {
      builder.password(encryptionService.encrypt(user.getPassword()));
    }

    return userRepository.save(builder.build());
  }

  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }
    userRepository.deleteById(id);
  }
}
