package fr.epita.zombie.user.domain.services;

public interface EncryptionService {
  String encrypt(String password);

  boolean matches(String rawPassword, String encryptedPassword);
}
