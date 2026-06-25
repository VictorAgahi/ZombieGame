package fr.epita.zombie.user.domain.services;

import fr.epita.zombie.user.application.mappers.UserMapper;
import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
import fr.epita.zombie.user.infrastructure.models.UserModel;
import fr.epita.zombie.user.infrastructure.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final EncryptionService encryptionService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(
      EncryptionService encryptionService, UserRepository userRepository, UserMapper userMapper) {
    this.encryptionService = encryptionService;
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public UserEntity register(UserEntity user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists");
    }

    user.changePassword(encryptionService.encrypt(user.getPassword()));
    UserModel model = userMapper.toModel(user);
    UserModel savedModel = userRepository.save(model);

    return userMapper.toEntity(savedModel);
  }

  public UserEntity getById(Long id) {
    UserModel model = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    return userMapper.toEntity(model);
  }

  public UserEntity update(Long id, UserEntity user) {
    UserModel model = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    model.setEmail(user.getEmail());
    model.setRole(user.getRole());

    if (user.getPassword() != null && !user.getPassword().isBlank()) {
      model.setPassword(encryptionService.encrypt(user.getPassword()));
    }

    UserModel updatedModel = userRepository.save(model);
    return userMapper.toEntity(updatedModel);
  }

  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }
    userRepository.deleteById(id);
  }
}
