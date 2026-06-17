package fr.epita.zombie.user.domain.services;

import fr.epita.zombie.user.application.dtos.requests.UserLoginRequest;
import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.responses.UserLoginResponse;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.application.mappers.UserMapper;
import fr.epita.zombie.user.domain.exceptions.BadCredentialsException;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
import fr.epita.zombie.user.domain.models.UserModel;
import fr.epita.zombie.user.infrastructure.entities.UserEntity;
import fr.epita.zombie.user.infrastructure.repositories.UserRepository;
import fr.epita.zombie.user.infrastructure.security.JwtUtils;
import fr.epita.zombie.user.infrastructure.security.UserDetailsConnected;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final EncryptionService encryptionService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final JwtUtils jwtUtils;

  public UserService(
      EncryptionService encryptionService,
      UserRepository userRepository,
      UserMapper userMapper,
      JwtUtils jwtUtils) {
    this.encryptionService = encryptionService;
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.jwtUtils = jwtUtils;
  }

  public UserLoginResponse authenticate(UserLoginRequest request) {
    UserModel loginModel = userMapper.toModel(request);
    UserEntity entity =
        userRepository
            .findByEmail(loginModel.getEmail())
            .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

    if (!encryptionService.matches(loginModel.getPassword(), entity.getPassword())) {
      throw new BadCredentialsException("Invalid email or password");
    }

    String token = jwtUtils.generateToken(new UserDetailsConnected(entity));
    return userMapper.toLoginResponse(token);
  }

  public UserResponse register(UserRegisterRequest request) {
    UserModel userModel = userMapper.toModel(request);
    if (userRepository.findByEmail(userModel.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists");
    }

    userModel.setPassword(encryptionService.encrypt(userModel.getPassword()));
    UserEntity entity = userMapper.toEntity(userModel);
    UserEntity savedEntity = userRepository.save(entity);

    return userMapper.toResponse(userMapper.toModel(savedEntity));
  }

  public UserResponse getById(Long id) {
    UserEntity entity =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    return userMapper.toResponse(userMapper.toModel(entity));
  }

  public UserResponse update(Long id, UserRegisterRequest request) {
    UserModel userModel = userMapper.toModel(request);
    UserEntity entity =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    entity.setEmail(userModel.getEmail());
    entity.setRole(userModel.getRole());

    if (userModel.getPassword() != null && !userModel.getPassword().isBlank()) {
      entity.setPassword(encryptionService.encrypt(userModel.getPassword()));
    }

    UserEntity updatedEntity = userRepository.save(entity);
    return userMapper.toResponse(userMapper.toModel(updatedEntity));
  }

  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }
}
