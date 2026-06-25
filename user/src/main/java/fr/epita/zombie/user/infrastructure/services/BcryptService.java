package fr.epita.zombie.user.infrastructure.services;

import fr.epita.zombie.user.domain.services.EncryptionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptService implements EncryptionService {

  private final PasswordEncoder passwordEncoder;

  public BcryptService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String encrypt(String password) {
    return passwordEncoder.encode(password);
  }

  @Override
  public boolean matches(String rawPassword, String encryptedPassword) {
    return passwordEncoder.matches(rawPassword, encryptedPassword);
  }
}
