package fr.epita.zombie.user.domain.services;

import fr.epita.zombie.user.application.dtos.requests.UserLoginRequest;
import fr.epita.zombie.user.application.dtos.requests.UserRegisterRequest;
import fr.epita.zombie.user.application.dtos.responses.UserLoginResponse;
import fr.epita.zombie.user.application.dtos.responses.UserResponse;
import fr.epita.zombie.user.application.mappers.UserMapper;
import fr.epita.zombie.user.domain.entities.UserEntity;
import fr.epita.zombie.user.domain.exceptions.BadCredentialsException;
import fr.epita.zombie.user.domain.exceptions.UserAlreadyExistsException;
import fr.epita.zombie.user.domain.exceptions.UserNotFoundException;
import fr.epita.zombie.user.infrastructure.models.UserModel;
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
    UserEntity loginEntity = userMapper.toEntity(request);
    UserModel model =
        userRepository
            .findByEmail(loginEntity.getEmail())
            .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

    if (!encryptionService.matches(loginEntity.getPassword(), model.getPassword())) {
      throw new BadCredentialsException("Invalid email or password");
    }

    String token = jwtUtils.generateToken(new UserDetailsConnected(model));
    return userMapper.toLoginResponse(token);
  }

  public UserResponse register(UserRegisterRequest request) {
    UserEntity userEntity = userMapper.toEntity(request);
    if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists");
    }

    userEntity.setPassword(encryptionService.encrypt(userEntity.getPassword()));
    UserModel model = userMapper.toModel(userEntity);
    UserModel savedModel = userRepository.save(model);

    return userMapper.toResponse(userMapper.toEntity(savedModel));
  }

  public UserResponse getById(Long id) {
    UserModel model =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    return userMapper.toResponse(userMapper.toEntity(model));
  }

  public UserResponse update(Long id, UserRegisterRequest request) {
    UserEntity userEntity = userMapper.toEntity(request);
    UserModel model =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    model.setEmail(userEntity.getEmail());
    model.setRole(userEntity.getRole());

    if (userEntity.getPassword() != null && !userEntity.getPassword().isBlank()) {
      model.setPassword(encryptionService.encrypt(userEntity.getPassword()));
    }

    UserModel updatedModel = userRepository.save(model);
    return userMapper.toResponse(userMapper.toEntity(updatedModel));
  }

  public void delete(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }
}
