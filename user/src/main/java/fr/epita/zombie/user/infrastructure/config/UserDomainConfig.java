package fr.epita.zombie.user.infrastructure.config;

import fr.epita.zombie.user.domain.ports.IUserRepository;
import fr.epita.zombie.user.domain.services.IEncryptionService;
import fr.epita.zombie.user.domain.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDomainConfig {

  @Bean
  public UserService userService(
      IEncryptionService encryptionService, IUserRepository userRepository) {
    return new UserService(encryptionService, userRepository);
  }
}
